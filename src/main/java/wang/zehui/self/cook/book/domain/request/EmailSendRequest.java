package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author wangzehui
 * @Date 2026/6/5 14:58
 */
@Data
public class EmailSendRequest {

    @Schema(description = "登录账号")
    @NotBlank(message = "登录账号不能为空")
    private String loginName;

    @Schema(description = "验证码")
    @NotNull(message = "验证码不能为空")
    private CaptchaRequest captchaRequest;
}
