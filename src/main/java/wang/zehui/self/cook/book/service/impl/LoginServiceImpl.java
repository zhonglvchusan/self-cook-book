package wang.zehui.self.cook.book.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.zehui.self.cook.book.common.consts.HeaderConst;
import wang.zehui.self.cook.book.common.consts.RedisKeyConst;
import wang.zehui.self.cook.book.common.consts.StringConst;
import wang.zehui.self.cook.book.common.utils.RedisUtil;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.LoginRequest;
import wang.zehui.self.cook.book.domain.request.UserRequest;
import wang.zehui.self.cook.book.domain.response.LoginResultResponse;
import wang.zehui.self.cook.book.service.ILoginService;
import wang.zehui.self.cook.book.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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

    private static final long USER_LOGIN_INFO_EXPIRE_TIME = 86400L;

    @Override
    public LoginResultResponse login(LoginRequest loginRequest) {
        return null;
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
}
