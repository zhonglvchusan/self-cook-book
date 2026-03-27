package wang.zehui.self.cook.book.service;

import org.apache.commons.lang3.tuple.Pair;

/**
 * @Author wangzehui
 * @Date 2026/3/20 11:01
 */
public interface IWxService {

    /**
     * @Description: 通过code获取用户手机号
     * @param code 微信返回的code
     * @Return: java.lang.String
     * @Author: wangzehui
     * @Date: 2026/3/26 15:32
     */
    String getUserPhoneNumberByCode(String code);

    /**
     * @Description: 通过code获取用户openId和unionId
     * @param code
     * @Return: org.apache.commons.lang3.tuple.Pair<java.lang.String,java.lang.String>
     * @Author: wangzehui
     * @Date: 2026/3/26 16:48
     */
    Pair<String, String> getUserOpenIdAndUnionId(String code);
}
