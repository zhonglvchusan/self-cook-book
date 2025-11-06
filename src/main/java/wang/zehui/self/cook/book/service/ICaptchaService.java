package wang.zehui.self.cook.book.service;

import wang.zehui.self.cook.book.domain.request.CaptchaRequest;
import wang.zehui.self.cook.book.domain.response.CaptchaResponse;

/**
 * @Author wangzehui
 * @Date 2025/11/6 14:05
 */
public interface ICaptchaService {

    /**
     * @Description: 生成验证码
     * @Return: wang.zehui.self.cook.book.domain.response.CaptchaResponse
     * @Author: wangzehui
     * @Date: 2025/11/6 14:07
     */
    CaptchaResponse generateCaptcha();

    /**
     * @Description: 校验验证码
     * @param captchaRequest 验证码请求
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2025/11/6 14:07
     */
    Boolean checkCaptcha(CaptchaRequest captchaRequest);
}
