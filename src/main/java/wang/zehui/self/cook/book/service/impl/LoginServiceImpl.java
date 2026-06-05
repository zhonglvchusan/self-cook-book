package wang.zehui.self.cook.book.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import wang.zehui.self.cook.book.common.consts.HeaderConst;
import wang.zehui.self.cook.book.common.consts.RedisKeyConst;
import wang.zehui.self.cook.book.common.consts.StringConst;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.UserPermission;
import wang.zehui.self.cook.book.common.enums.ErrorCodeEnum;
import wang.zehui.self.cook.book.common.enums.GenderEnum;
import wang.zehui.self.cook.book.common.enums.LoginDeviceEnum;
import wang.zehui.self.cook.book.common.enums.UserAdminFlagEnum;
import wang.zehui.self.cook.book.common.utils.ConvertUtil;
import wang.zehui.self.cook.book.common.utils.RedisUtil;
import wang.zehui.self.cook.book.domain.entity.Menu;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.*;
import wang.zehui.self.cook.book.domain.response.LoginResultResponse;
import wang.zehui.self.cook.book.domain.response.MenuInfoResponse;
import wang.zehui.self.cook.book.domain.response.RoleInfoResponse;
import wang.zehui.self.cook.book.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author wangzehui
 * @Date 2025/10/31 14:37
 */
@Service
@Slf4j
public class LoginServiceImpl implements ILoginService, StpInterface {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ICaptchaService captchaService;

    @Autowired
    private Validator validator;

    @Autowired
    private IWxService wxService;

    @Autowired
    private IRoleMenuService roleMenuService;

    @Autowired
    private IRoleUserService roleUserService;

    private static final long USER_LOGIN_INFO_EXPIRE_TIME = 86400L;

    @Override
    public LoginResultResponse login(LoginRequest loginRequest) {
        LoginDeviceEnum loginDeviceEnum = this.checkLoginDevice(loginRequest.getLoginDevice());

        // 验证验证码是否正确
        captchaService.checkCaptcha(loginRequest.getCaptchaRequest());

        User user = userService.getByLoginName(loginRequest.getLoginName());

        if (!Objects.equals(DigestUtils.md5Hex(loginRequest.getPassword() + loginRequest.getLoginName()), user.getLoginPassword())) {
            throw new BusinessException(ErrorCodeEnum.LOGIN_NAME_OR_PASSWORD_ERROR);
        }

        return this.login(user, loginDeviceEnum);
    }

    @Override
    public LoginResultResponse wxLogin(WxLoginRequest loginRequest) {
        LoginDeviceEnum loginDeviceEnum = this.checkLoginDevice(loginRequest.getLoginDevice());

        // 向微信换取用户手机号，个人版不支持，登陆后让用户自己添加手机号
        // String userLoginName = wxService.getUserPhoneNumberByCode(loginRequest.getPhoneCode());
        // loginRequest.setLoginName(userLoginName);
        // 向微信换取用户openId
        Pair<String, String> userOpenIdAndUnionId = wxService.getUserOpenIdAndUnionId(loginRequest.getLoginCode());
        User user = userService.getByOpenIdOrUnionId(userOpenIdAndUnionId);
        // 如果没查到用户，则自动创建用户
        if (Objects.isNull(user)) {
            UserAddRequest userAddRequest = this.buildMiniAppAddUser(userOpenIdAndUnionId);
            userService.registerUser(userAddRequest);
            user = userService.getByOpenIdOrUnionId(userOpenIdAndUnionId);
        }

        return this.login(user, loginDeviceEnum);
    }

    @Override
    public LoginResultResponse wxPhoneLogin(WxPhoneLoginRequest phoneLoginRequest) {
        LoginRequest loginRequest = new LoginRequest();
        BeanUtils.copyProperties(phoneLoginRequest, loginRequest);
        return this.login(loginRequest);
    }

    @Override
    public UserRequest getLoginUser(String loginId, HttpServletRequest request) {
        if (StringUtils.isBlank(loginId)) {
            return null;
        }

        // 从sa-token的loginId中获取userId
        String userId = this.getUserIdByLoginId(loginId);
        if (StringUtils.isBlank(userId)) {
            return null;
        }

        // 先从缓存中获取
        String miniApp = request.getHeader(HeaderConst.MINI_APP);
        String cacheKey = redisUtil.generateRedisKey(StringUtils.isBlank(miniApp) ? RedisKeyConst.ADMIN : RedisKeyConst.API, RedisKeyConst.REQUEST_USER + userId);
        UserRequest userRequest = redisUtil.get(cacheKey, UserRequest.class);
        // 缓存中有，直接读缓存返回
        if (!Objects.isNull(userRequest)) {
            return userRequest;
        }

        // 查询当前用户在数据库中是否存在
        User user = userService.getById(userId);
        if (Objects.isNull(user)) {
            return null;
        }

        userRequest = this.loadLoginInfo(user);
        // 缓存1天
        redisUtil.set(cacheKey, userRequest, USER_LOGIN_INFO_EXPIRE_TIME);

        return userRequest;
    }

    @Override
    public UserRequest loadLoginInfo(User user) {
        if (Objects.isNull(user)) {
            return null;
        }

        String cacheKey = redisUtil.generateRedisKey(Objects.equals(2, user.getAdminFlag()) ? RedisKeyConst.API : RedisKeyConst.ADMIN, RedisKeyConst.REQUEST_USER + user.getId());
        UserRequest userRequest = redisUtil.get(cacheKey, UserRequest.class);
        if (!Objects.isNull(userRequest)) {
            return userRequest;
        }

        // 缓存不存在
        userRequest = new UserRequest();
        BeanUtils.copyProperties(user, userRequest);
        userRequest.setUserId(user.getId());
        userRequest.setIsAdmin(!Objects.equals(UserAdminFlagEnum.USER.getCode(), user.getAdminFlag()));

        redisUtil.set(cacheKey, userRequest, USER_LOGIN_INFO_EXPIRE_TIME);

        return userRequest;
    }

    @Override
    public Boolean logout(UserRequest userRequest) {
        StpUtil.logout();

        String userInfoCacheKey = redisUtil.generateRedisKey(!userRequest.getIsAdmin() ? RedisKeyConst.API : RedisKeyConst.ADMIN, RedisKeyConst.REQUEST_USER + userRequest.getUserId());
        String userPermissionCacheKey = redisUtil.generateRedisKey(RedisKeyConst.ADMIN, RedisKeyConst.LOGIN_USER_PERMISSION + userRequest.getUserId());
        redisUtil.del(userInfoCacheKey, userPermissionCacheKey);
        return true;
    }

    /**
     * @Description: 从loginId中获取userId
     * @param loginId 登录ID
     * @Return: java.lang.String
     * @Author: wangzehui
     * @Date: 2026/3/26 17:03
     */
    private String getUserIdByLoginId(String loginId) {
        if (StringUtils.isBlank(loginId)) {
            return null;
        }

        try {
            return loginId.split(StringConst.COLON)[1];
        } catch (Exception e) {
            log.error("从 loginId 中解析 userId 异常, LoginId: {}", loginId);
            return null;
        }
    }

    /**
     * @Description: 构建增加小程序用户表单
     * @param userOpenIdAndUnionId 微信小程序返回的openId和unionId
     * @Return: wang.zehui.self.cook.book.domain.request.UserAddRequest
     * @Author: wangzehui
     * @Date: 2026/3/26 17:06
     */
    private UserAddRequest buildMiniAppAddUser(Pair<String, String> userOpenIdAndUnionId) {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setOpenId(userOpenIdAndUnionId.getLeft());
        userAddRequest.setUnionId(userOpenIdAndUnionId.getRight());
        userAddRequest.setNickname("微信用户" + UUID.randomUUID().toString().substring(0, 6));
        userAddRequest.setGender(GenderEnum.UNKNOWN.getValue());
        userAddRequest.setAdminFlag(2);
        return userAddRequest;
    }

    /**
     * @Description: 加载登录结果
     * @param userRequest 登录用户
     * @Return: wang.zehui.self.cook.book.domain.response.LoginResultResponse
     * @Author: wangzehui
     * @Date: 2026/4/17 15:28
     */
    @Override
    public LoginResultResponse getLoginResult(UserRequest userRequest) {
        LoginResultResponse loginResultResponse = new LoginResultResponse();
        BeanUtils.copyProperties(userRequest, loginResultResponse);

        // 前端菜单和功能点清单
        List<RoleInfoResponse> roles = roleUserService.getRoleByUserId(userRequest.getUserId());
        List<Menu> menus = roleMenuService.getMenuList(ConvertUtil.convertList(roles, RoleInfoResponse::getId), Objects.equals(UserAdminFlagEnum.SUPER_ADMIN.getCode(), userRequest.getAdminFlag()));
        loginResultResponse.setMenus(menus.stream()
                .map(menu -> {
                    MenuInfoResponse menuInfoResponse = new MenuInfoResponse();
                    BeanUtils.copyProperties(menu, menuInfoResponse);
                    return menuInfoResponse;
                }).collect(Collectors.toList()));

        return loginResultResponse;
    }

    /**
     * @Description: saToken用户权限获取
     * @param loginId 登录ID
     * @param loginType 登录类型
     * @Return: java.util.List<java.lang.String>
     * @Author: wangzehui
     * @Date: 2026/4/8 16:42
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String userId = this.getUserIdByLoginId(String.valueOf(loginId));
        if (StringUtils.isBlank(userId)) {
            return Collections.emptyList();
        }

        UserPermission userPermission = this.loadUserPermission(userId);
        return userPermission.getPermissions();
    }

    /**
     * @Description: saToken用户角色获取
     * @param loginId 登录ID
     * @param loginType 登录类型
     * @Return: java.util.List<java.lang.String>
     * @Author: wangzehui
     * @Date: 2026/4/8 16:43
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String userId = this.getUserIdByLoginId(String.valueOf(loginId));
        if (StringUtils.isBlank(userId)) {
            return Collections.emptyList();
        }

        UserPermission userPermission = this.loadUserPermission(userId);
        return userPermission.getRoles();
    }

    /**
     * @Description: 加载用户角色、权限
     * @param userId 用户ID
     * @Return: wang.zehui.self.cook.book.common.domain.UserPermission
     * @Author: wangzehui
     * @Date: 2026/4/17 14:49
     */
    private synchronized UserPermission loadUserPermission(String userId) {
        UserPermission userPermission = new UserPermission();
        userPermission.setPermissions(new ArrayList<>());
        userPermission.setRoles(new ArrayList<>());

        // 获取用户角色并设置
        List<RoleInfoResponse> userRoles = roleUserService.getRoleByUserId(userId);
        userPermission.getRoles().addAll(ConvertUtil.convertList(userRoles, RoleInfoResponse::getRoleCode));

        User user = userService.getById(userId);
        List<Menu> menus = roleMenuService.getMenuList(ConvertUtil.convertList(userRoles, RoleInfoResponse::getId), Objects.equals(UserAdminFlagEnum.SUPER_ADMIN.getCode(), user.getAdminFlag()));

        // 添加用户权限
        menus.stream()
                .filter(menu -> !Objects.isNull(menu.getPermsType()))
                .filter(menu -> !StringUtils.isBlank(menu.getApiPerms()))
                .map(menu -> menu.getApiPerms().split(","))
                .flatMap(Arrays::stream)
                .forEach(userPermission.getPermissions()::add);

        // 设置缓存
        String cacheKey = redisUtil.generateRedisKey(RedisKeyConst.ADMIN, RedisKeyConst.LOGIN_USER_PERMISSION + userId);
        redisUtil.set(cacheKey, userPermission, USER_LOGIN_INFO_EXPIRE_TIME);

        return userPermission;
    }

    /**
     * @Description: 校验登录设备，校验通过返回对应设备的枚举，未通过则抛出异常
     * @param loginDevice 登录设备
     * @Return: wang.zehui.self.cook.book.common.enums.LoginDeviceEnum
     * @Author: wangzehui
     * @Date: 2026/6/4 10:08
     */
    private LoginDeviceEnum checkLoginDevice(Integer loginDevice) {
        LoginDeviceEnum loginDeviceEnum = LoginDeviceEnum.getEnumByType(loginDevice);
        if (Objects.isNull(loginDeviceEnum)) {
            throw new BusinessException("登录设备不支持!");
        }

        return loginDeviceEnum;
    }

    private LoginResultResponse login(User user, LoginDeviceEnum loginDeviceEnum) {
        // 验证用户状态
        if (user.getDeleted()) {
            throw new BusinessException(ErrorCodeEnum.USER_DELETED);
        }

        if (!Objects.equals(0, user.getState())) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_ACTIVE);
        }

        String saTokenLoginId = user.getAdminFlag() + StringConst.COLON + user.getId();

        // 登录
        StpUtil.login(saTokenLoginId, String.valueOf(loginDeviceEnum.getDescription()));

        // 获取登录信息
        UserRequest userRequest = this.loadLoginInfo(user);

        // 获取登录结果
        String token = StpUtil.getTokenValue();
        LoginResultResponse loginResult = this.getLoginResult(userRequest);

        // 设置token
        loginResult.setToken(token);

        this.loadUserPermission(user.getId());

        return loginResult;
    }
}
