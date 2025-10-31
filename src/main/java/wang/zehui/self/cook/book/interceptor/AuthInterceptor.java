package wang.zehui.self.cook.book.interceptor;

import cn.dev33.satoken.stp.StpUtil;
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
import wang.zehui.self.cook.book.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

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

        // 获取用户信息，如果可以的话
        String token = StpUtil.getTokenValue();
        String loginId = (String) StpUtil.getLoginIdByToken(token);
        UserRequest userRequest = userService.getLoginUser(loginId, request);

        Method method = ((HandlerMethod) handler).getMethod();
        if (method.isAnnotationPresent(NoNeedLogin.class)) {
            return true;
        }


        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
