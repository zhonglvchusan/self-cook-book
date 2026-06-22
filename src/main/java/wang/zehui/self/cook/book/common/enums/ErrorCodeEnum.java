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

    // 管理后台10000起
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
    LOGIN_NAME_OR_PASSWORD_ERROR(10011, "用户名或密码错误"),

    // 对用户11000起
    RESTAURANT_NOT_EXIST(11001, "餐厅不存在，请换一家吧~"),
    RESTAURANT_NOT_PERMISSION(11002, "该餐厅不属于你，请勿使用违法手段修改他人餐厅，被查到会封禁账号哦！"),
    PHONE_NUMBER_EXIST(11003, "手机号已存在"),
    EMAIL_NOT_EXIST(11004, "邮箱不存在"),
    USER_RESTAURANT_NOT_EXIST(11005, "您当前还没有开餐厅，请投资一家吧~"),
    CATEGORY_NOT_EXIST(11006, "餐厅分类不存在"),


    // 系统错误50000起
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
