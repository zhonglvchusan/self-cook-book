package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.properties.EmailServerProperties;
import wang.zehui.self.cook.book.domain.response.CaptchaResponse;

/**
 * @Author wangzehui
 * @Date 2026/6/5 14:03
 */
@Data
public class EmailSendCodeRequest {

    @Schema(description = "接收验证码邮箱")
    private String emailAddress;

    @Schema(description = "验证码")
    private CaptchaResponse captcha;

    @Schema(description = "邮箱服务器配置")
    private EmailServerProperties properties;
}
