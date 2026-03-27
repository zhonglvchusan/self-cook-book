package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wangzehui
 * @Date 2025/5/13 10:59
 */
@AllArgsConstructor
@Getter
public enum WxMiniAppErrorEnum implements BaseEnum {

    SYSTEM_ERROR(-1, "系统繁忙，请开发者稍后再试"),

    INVALID_CREDENTIAL(40001, "获取access_token时AppSecret错误，或者access_token无效。请开发者认真比对AppSecret的正确性，或查看是否正在为恰当的公众号调用接口"),

    INVALID_APPID(40013, "不合法的appID，请开发者检查appID的正确性，避免异常字符，注意大小写"),

    INVALID_GRANT_TYPE(40002, "不合法的grant_type"),

    INVALID_SECRET(40125, "请检查secret的正确性，避免异常字符，注意大小写"),

    INVALID_IP(40164, "调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置。"),

    MISSING_APP_SECRET(41004, "缺少secret参数"),

    NO_USE_TOKEN(50004, "禁止使用token接口"),

    ACCOUNT_FREEZE(50007, "帐号已冻结"),

    FREEZE_APP_SECRET(40243, "AppSecret已被冻结，请登录小程序平台解冻后再次调用"),

    NEED_DEDICATED_TOKEN(61024, "第三方平台API需要使用第三方平台专用token"),

    INVALID_CODE(40029, "code 无效"),

    FREQUENTLY_CALL(45011, "API调用太频繁，请稍后再试"),

    CODE_BLOCKED(40026, "高风险等级用户，小程序登录拦截。风险等级详见用户安全解方案"),

    CODE_BEEN_USED(40163, "code 已使用"),
    ;

    private final Integer value;

    private final String description;

    /**
     * @Description: 通过枚举值获取枚举信息
     * @param: value
     * @Return: java.lang.String
     * @Author: wangzehui
     * @Date: 2025/5/13 11:21
     */
    public static String getEnumDesc(Integer value) {
        for (WxMiniAppErrorEnum item : WxMiniAppErrorEnum.values()) {
            if (item.equalsValue(value)) {
                return item.getDescription();
            }
        }
        return null;
    }
}
