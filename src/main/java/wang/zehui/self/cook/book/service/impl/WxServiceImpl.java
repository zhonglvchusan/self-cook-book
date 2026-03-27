package wang.zehui.self.cook.book.service.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wang.zehui.self.cook.book.common.config.WechatMiniAppConfigProperties;
import wang.zehui.self.cook.book.common.consts.RedisKeyConst;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.SystemEnvironment;
import wang.zehui.self.cook.book.common.enums.SystemEnvironmentEnum;
import wang.zehui.self.cook.book.common.enums.WxMiniAppErrorEnum;
import wang.zehui.self.cook.book.common.utils.RedisUtil;
import wang.zehui.self.cook.book.service.IWxService;

import java.util.Objects;

/**
 * @Author wangzehui
 * @Date 2026/3/20 11:01
 */
@Service
@Slf4j
public class WxServiceImpl implements IWxService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WechatMiniAppConfigProperties wechatMiniAppConfigProperties;

    @Autowired
    private SystemEnvironment systemEnvironment;

    @Override
    public String getUserPhoneNumberByCode(String code) {
        // 开发环境返回固定手机号
        if (Objects.equals(SystemEnvironmentEnum.DEV, systemEnvironment.getCurrentEnvironment())) {
            return "12345678901";
        }

        String phoneNumberUrl = wechatMiniAppConfigProperties.getPhoneNumberUrl(this.getMiniAppAccessToken());

        JSONObject params = new JSONObject();
        params.put("code", code);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<JSONObject> entity = new HttpEntity<>(params, headers);

        JSONObject response = restTemplate.postForObject(phoneNumberUrl, entity, JSONObject.class);

        if (Objects.isNull(response)) {
            log.info("获取微信小程序用户手机号，返回结果为空");
            throw new BusinessException("获取手机号失败，请稍后尝试");
        }

        if (response.containsKey("errcode")) {
            // 如果异常code为0, 说明成功
            if (!Objects.equals(0, response.getInteger("errcode"))) {
                String errCodeDescription = WxMiniAppErrorEnum.getEnumDesc(response.getInteger("errcode"));
                log.error("获取微信小程序用户手机号异常，错误码：{}，错误描述：{}", response.getInteger("errcode"), errCodeDescription);
                throw new BusinessException("获取手机号失败，请稍后尝试");
            }
        }

        JSONObject phoneInfo = response.getJSONObject("phone_info");
        return phoneInfo.getString("phoneNumber");
    }

    @Override
    public Pair<String, String> getUserOpenIdAndUnionId(String code) {
        String code2SessionUrl = wechatMiniAppConfigProperties.getCode2SessionUrl(code);

        // 微信返回的是json字符串，但是响应头设置为了text/plain，只能接收后再转换
        String responseString = restTemplate.getForObject(code2SessionUrl, String.class);
        JSONObject response = JSONObject.parseObject(responseString);

        if (Objects.isNull(response)) {
            log.info("获取微信小程序用户openId和unionId，返回结果为空");
            throw new BusinessException("获取微信小程序用户openId和unionId失败，请稍后尝试");
        }

        if (response.containsKey("errcode")) {
            // 如果异常code为0, 说明成功
            if (!Objects.equals(0, response.getInteger("errcode"))) {
                String errCodeDescription = WxMiniAppErrorEnum.getEnumDesc(response.getInteger("errcode"));
                log.error("获取微信小程序用户openId和unionId异常，错误码：{}，错误描述：{}", response.getInteger("errcode"), errCodeDescription);
                throw new BusinessException("获取微信小程序用户openId和unionId失败，请稍后尝试");
            }
        }

        String openid = response.getString("openid");
        String unionid = response.getString("unionid");

        return Pair.of(openid, unionid);
    }

    /**
     * @Description: 获取微信小程序请求accessToken
     * @Return: java.lang.String
     * @Author: wangzehui
     * @Date: 2026/3/26 16:04
     */
    private String getMiniAppAccessToken() {
        String accessTokenRedisKey = redisUtil.generateRedisKey(RedisKeyConst.WX_MINI_APP_PREFIX, RedisKeyConst.WX_MINI_APP_ACCESS_TOKEN);
        if (redisUtil.hasKey(accessTokenRedisKey)) {
            log.info("从redis中取出微信accessToken");
            return redisUtil.get(accessTokenRedisKey, String.class);
        }

        try {
            // 获取请求地址
            String accessTokenUrl = wechatMiniAppConfigProperties.getAccessTokenUrl();
            JSONObject response = restTemplate.getForObject(accessTokenUrl, JSONObject.class);

            if (Objects.isNull(response)) {
                log.info("获取微信小程序accessToken，返回结果为空");
                throw new BusinessException("获取微信小程序accessToken失败，请稍后尝试");
            }

            String accessToken = response.getString("access_token");
            if (StringUtils.isBlank(accessToken)) {
                log.info("获取微信小程序accessToken，返回结果accessToken为空");
                if (response.containsKey("errcode")) {
                    String errCodeDescription = WxMiniAppErrorEnum.getEnumDesc(response.getInteger("errcode"));
                    log.error("获取微信小程序accessToken异常，错误码：{}，错误描述：{}", response.getInteger("errcode"), errCodeDescription);
                }
                throw new BusinessException("获取微信小程序accessToken失败，请稍后尝试");
            }

            // 存入redis中
            redisUtil.set(accessTokenRedisKey, accessToken, response.getLong("expires_in"));

            return accessToken;
        } catch (Exception e) {
            log.error("获取微信小程序accessToken异常", e);
            throw new BusinessException("获取微信小程序accessToken失败，请稍后尝试");
        }
    }
}
