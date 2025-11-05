package wang.zehui.self.cook.book.interceptor;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaAnnotationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import wang.zehui.self.cook.book.annoation.NoNeedLogin;
import wang.zehui.self.cook.book.domain.request.UserRequest;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.enums.ErrorCodeEnum;
import wang.zehui.self.cook.book.enums.UserAdminFlagEnum;
import wang.zehui.self.cook.book.service.IUserService;
import wang.zehui.self.cook.book.utils.RequestUtil;
import wang.zehui.self.cook.book.utils.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author wangzehui
 * @Date 2025/10/28 14:51
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果是option请求，直接返回
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return false;
        }

        // 校验是否是方法拦截器
        boolean isHandler = handler instanceof HandlerMethod;
        if (!isHandler) {
            return true;
        }

        try {
            // 获取用户信息，如果可以的话
            String token = StpUtil.getTokenValue();
            String loginId = (String) StpUtil.getLoginIdByToken(token);
            UserRequest userRequest = userService.getLoginUser(loginId, request);

            Method method = ((HandlerMethod) handler).getMethod();
            if (method.isAnnotationPresent(NoNeedLogin.class)) {
                this.updateActiveTimeout(userRequest);
                RequestUtil.setUserRequest(userRequest);
                return true;
            }

            // 校验是否登录
            if (Objects.isNull(userRequest)) {
                ResponseUtil.write(response, ResponseDTO.error(ErrorCodeEnum.LOGIN_STATE_INVALID));
                return false;
            }

            this.updateActiveTimeout(userRequest);

            // 校验角色权限
            RequestUtil.setUserRequest(userRequest);
            if (SaAnnotationStrategy.instance.isAnnotationPresent.apply(method, SaIgnore.class)) {
                return true;
            }

            // 如果是超级管理员不校验权限
            if (Objects.equals(UserAdminFlagEnum.SUPER_ADMIN.getCode(), userRequest.getAdminFlag())) {
                return true;
            }

            SaAnnotationStrategy.instance.checkMethodAnnotation.accept(method);
        } catch (SaTokenException e) {
            /*
             * sa-token 异常状态码
             * 具体请看： https://sa-token.cc/doc.html#/fun/exception-code
             */
            int code = e.getCode();
            if (Objects.equals(11041, code) || Objects.equals(11051, code)) {
                ResponseUtil.write(response, ResponseDTO.error(ErrorCodeEnum.NO_PERMISSION));
            } else if (Objects.equals(11016, code)) {
                ResponseUtil.write(response, ResponseDTO.error(ErrorCodeEnum.LOGIN_ACTIVE_TIMEOUT));
            } else if (code >= 11011 && code <= 11015) {
                ResponseUtil.write(response, ResponseDTO.error(ErrorCodeEnum.LOGIN_STATE_INVALID));
            } else {
                ResponseUtil.write(response, ResponseDTO.error(ErrorCodeEnum.PARAM_ERROR));
            }

            return false;
        } catch (Throwable e) {
            ResponseUtil.write(response, ResponseDTO.error(ErrorCodeEnum.SYSTEM_ERROR));
            log.error("权限校验异常", e);
            return false;
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // 清除之前设置的ThreadLocal
        RequestUtil.remove();
    }

    /**
     * @Description: 更新token活跃时间
     * @param userRequest 用户信息
     * @Return: void
     * @Author: wangzehui
     * @Date: 2025/11/5 11:50
     */
    private void updateActiveTimeout(UserRequest userRequest) {
        if (Objects.isNull(userRequest)) {
            return;
        }
        StpUtil.updateLastActiveToNow();
    }
}
