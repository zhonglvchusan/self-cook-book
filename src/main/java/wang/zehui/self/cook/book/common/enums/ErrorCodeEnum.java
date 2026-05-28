package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wangzehui
 * @Date 2025/11/5 14:41
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum implements BaseEnum {

    LOGIN_STATE_INVALID(10001, "您还未登录或登录失效，请重新登录！"),
    NO_PERMISSION(10002, "对不起，您没有权限访问此内容！"),
    LOGIN_ACTIVE_TIMEOUT(10003, "长时间未操作，请重新登录"),
    PARAM_ERROR(10004, "参数错误"),
    LOGIN_NAME_EXIST(10005, "登录名重复"),
    USER_NOT_EXIST(10006, "用户不存在"),
    USER_NOT_ACTIVE(10007, "用户已禁用,请联系工作人员"),
    USER_DELETED(10008, "用户已删除,请联系工作人员"),
    BEFORE_PASSWORD_ERROR(10009, "原密码错误"),
    PASSWORD_NOT_CHANGE(10010, "原密码不能与新密码相同"),


    SYSTEM_ERROR(50001, "系统错误"),

    ;

    private final Integer code;

    private final String message;

    @Override
    public Object getValue() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.message;
    }
}
