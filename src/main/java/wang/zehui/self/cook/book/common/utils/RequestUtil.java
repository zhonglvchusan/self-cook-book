package wang.zehui.self.cook.book.common.utils;

import wang.zehui.self.cook.book.domain.request.UserRequest;

import java.util.Objects;

/**
 * @Author wangzehui
 * @Date 2025/10/28 15:22
 */
public class RequestUtil {

    private static final ThreadLocal<UserRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<>();

    public static void setUserRequest(UserRequest userRequest) {
        if (Objects.isNull(userRequest)) {
            return;
        }
        REQUEST_THREAD_LOCAL.set(userRequest);
    }

    public static UserRequest getUserRequest() {
        return REQUEST_THREAD_LOCAL.get();
    }

    public static String getUserId() {
        UserRequest userRequest = getUserRequest();
        return Objects.isNull(userRequest) ? null : userRequest.getUserId();
    }

    public static void remove() {
        REQUEST_THREAD_LOCAL.remove();
    }
}
