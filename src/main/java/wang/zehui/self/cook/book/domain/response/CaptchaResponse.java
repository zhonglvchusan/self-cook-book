package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author wangzehui
 * @Date 2025/11/6 14:05
 */
@Data
@Schema(description = "验证码响应")
public class CaptchaResponse {

    @Schema(description = "验证码id")
    private String captchaId;

    @Schema(description = "验证码图片base64")
    private String captchaBase64Image;

    @Schema(description = "验证码过期时间")
    private Integer expireSeconds;

    @Schema(description = "验证码")
    private String captchaCode;
}
