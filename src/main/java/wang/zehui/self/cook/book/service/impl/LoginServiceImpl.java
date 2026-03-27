package wang.zehui.self.cook.book.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
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
import wang.zehui.self.cook.book.common.enums.GenderEnum;
import wang.zehui.self.cook.book.common.enums.LoginDeviceEnum;
import wang.zehui.self.cook.book.common.enums.UserAdminFlagEnum;
import wang.zehui.self.cook.book.common.utils.RedisUtil;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.LoginRequest;
import wang.zehui.self.cook.book.domain.request.UserAddRequest;
import wang.zehui.self.cook.book.domain.request.UserRequest;
import wang.zehui.self.cook.book.domain.response.LoginResultResponse;
import wang.zehui.self.cook.book.service.ICaptchaService;
import wang.zehui.self.cook.book.service.ILoginService;
import wang.zehui.self.cook.book.service.IUserService;
import wang.zehui.self.cook.book.service.IWxService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author wangzehui
 * @Date 2025/10/31 14:37
 */
@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {

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

    private static final long USER_LOGIN_INFO_EXPIRE_TIME = 86400L;

    @Override
    public LoginResultResponse login(LoginRequest loginRequest) {
        LoginDeviceEnum loginDeviceEnum = LoginDeviceEnum.getEnumByType(loginRequest.getLoginDevice());
        if (Objects.isNull(loginDeviceEnum)) {
            throw new BusinessException("登录设备不支持!");
        }

        Pair<String, String> userOpenIdAndUnionId = Pair.of(StringConst.EMPTY, StringConst.EMPTY);
        // 不需要向小程序换手机号，则获取验证码
        if (!loginRequest.getMiniAppFlag()) {
            this.valid(loginRequest, LoginRequest.AdminGroup.class);
            // 验证验证码是否正确
            captchaService.checkCaptcha(loginRequest.getCaptchaRequest());
        } else {
            this.valid(loginRequest, LoginRequest.ApiGroup.class);
            // 向微信换取用户手机号
            String userLoginName = wxService.getUserPhoneNumberByCode(loginRequest.getPhoneCode());
            loginRequest.setLoginName(userLoginName);
            userOpenIdAndUnionId = wxService.getUserOpenIdAndUnionId(loginRequest.getLoginCode());
        }

        User user = userService.getByLoginName(loginRequest.getLoginName());

        // 如果没查到用户，且是微信登录，则自动创建用户
        if (Objects.isNull(user)) {
            if (!loginRequest.getMiniAppFlag()) {
                throw new BusinessException("登录名或密码错误");
            }
            UserAddRequest userAddRequest = this.buildMiniAppAddUser(loginRequest.getLoginName(), userOpenIdAndUnionId);
            userService.registerUser(userAddRequest);
            user = userService.getByLoginName(loginRequest.getLoginName());
        }

        // 验证用户状态
        if (user.getDeleted()) {
            throw new BusinessException("用户已删除,请联系工作人员");
        }

        if (user.getState()) {
            throw new BusinessException("用户已禁用,请联系工作人员");
        }

        String saTokenLoginId = UserAdminFlagEnum.USER.getValue() + StringConst.COLON + user.getId();

        // 登录
        StpUtil.login(saTokenLoginId, String.valueOf(loginDeviceEnum.getDescription()));

        // 获取登录信息
        UserRequest userRequest = this.loadLoginInfo(user);

        // 获取登录结果
        String token = StpUtil.getTokenValue();
        LoginResultResponse loginResult = this.getLoginResult(userRequest);

        // 设置token
        loginResult.setToken(token);

        return loginResult;
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

        redisUtil.set(cacheKey, userRequest, USER_LOGIN_INFO_EXPIRE_TIME);

        return userRequest;
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
     * @Description: 校验对象指定的校验组
     * @param validObject 需要校验的对象
     * @param validGroup 校验组
     * @Return: void
     * @Author: wangzehui
     * @Date: 2026/3/26 17:04
     */
    private void valid(Object validObject, Class<?> validGroup) {
        Set<ConstraintViolation<Object>> validate = validator.validate(validObject, validGroup);
        if (!CollectionUtils.isEmpty(validate)) {
            String message = validate.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(","));
            throw new BusinessException(message);
        }
    }

    /**
     * @Description: 构建增加小程序用户表单
     * @param phoneNumber 用户手机号
     * @param userOpenIdAndUnionId 微信小程序返回的openId和unionId
     * @Return: wang.zehui.self.cook.book.domain.request.UserAddRequest
     * @Author: wangzehui
     * @Date: 2026/3/26 17:06
     */
    private UserAddRequest buildMiniAppAddUser(String phoneNumber, Pair<String, String> userOpenIdAndUnionId) {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setLoginName(phoneNumber);
        userAddRequest.setOpenId(userOpenIdAndUnionId.getLeft());
        userAddRequest.setUnionId(userOpenIdAndUnionId.getRight());
        userAddRequest.setNickname("微信用户" + phoneNumber.substring(7));
        userAddRequest.setGender(GenderEnum.UNKNOWN.getValue());
        userAddRequest.setPhoneNumber(phoneNumber);
        userAddRequest.setAdminFlag(2);
        return userAddRequest;
    }

    private LoginResultResponse getLoginResult(UserRequest userRequest) {
        LoginResultResponse loginResultResponse = new LoginResultResponse();
        BeanUtils.copyProperties(userRequest, loginResultResponse);
        return loginResultResponse;
    }
}
