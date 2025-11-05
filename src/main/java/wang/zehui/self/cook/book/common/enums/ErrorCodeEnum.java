package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wangzehui
 * @Date 2025/11/5 14:41
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    LOGIN_STATE_INVALID(10001, "您还未登录或登录失效，请重新登录！"),
    NO_PERMISSION(10002, "对不起，您没有权限访问此内容！"),
    LOGIN_ACTIVE_TIMEOUT(10003, "长时间未操作，请重新登录"),
    PARAM_ERROR(10004, "参数错误"),


    SYSTEM_ERROR(50001, "系统错误"),

    ;

    private final Integer code;

    private final String message;

}
