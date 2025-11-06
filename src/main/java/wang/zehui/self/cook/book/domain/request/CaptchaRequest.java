package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author wangzehui
 * @Date 2025/11/6 14:07
 */
@Data
@Schema(description = "验证码请求")
public class CaptchaRequest {

    @Schema(description = "验证码id")
    @NotBlank(message = "验证码标识不能为空")
    private String captchaId;

    @Schema(description = "验证码结果")
    @NotBlank(message = "验证码不能为空")
    private String captchaCode;
}
