package wang.zehui.self.cook.book.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author wangzehui
 * @Date 2026/3/26 15:34
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat.mini-app")
public class WechatMiniAppConfigProperties {

    /**
     * 微信小程序主域名
     */
    public String domain;

    /**
     * 微信小程序appId
     */
    public String appId;

    /**
     * 微信小程序appSecret
     */
    public String appSecret;

    /**
     * 微信小程序获取用户手机号url
     */
    public String phoneNumberUrl;

    /**
     * 获取access_token的参数
     */
    public AccessToken accessToken;

    /**
     * 获取code2Session的参数
     */
    public Code2Session code2Session;

    @Data
    public static class AccessToken {

        /**
         * 获取access_token的验证字符串
         */
        public String grantType;

        /**
         * 获取access_token的url
         */
        public String url;
    }

    @Data
    public static class Code2Session {

        /**
         * 获取code2Session的url
         */
        public String url;

        /**
         * 获取code2Session的验证字符串
         */
        public String grantType;
    }

    /**
     * @Description: 获取请求access_token的url
     * @Return: java.lang.String
     * @Author: wangzehui
     * @Date: 2025/5/13 13:45
     */
    public String getAccessTokenUrl() {
        return String.format(domain + accessToken.url + "?grant_type=%s&appid=%s&secret=%s", accessToken.grantType, appId, appSecret);
    }

    /**
     * @Description: 获取请求用户手机号的url
     * @Return: java.lang.String
     * @Author: wangzehui
     * @Date: 2025/5/13 14:13
     */
    public String getPhoneNumberUrl(String accessToken) {
        return String.format(domain + phoneNumberUrl + "?access_token=%s", accessToken);
    }

    /**
     * @Description: 获取请求用户openId的url
     * @param: code 前端获取的code
     * @Return: java.lang.String
     * @Author: wangzehui
     * @Date: 2025/7/29 16:05
     */
    public String getCode2SessionUrl(String code) {
        return String.format(domain + code2Session.url + "?appid=%s&secret=%s&grant_type=%s&js_code=%s", appId, appSecret, code2Session.grantType, code);
    }
}
