package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author wangzehui
 * @Date 2026/6/5 14:11
 */
@Data
public class ForgetPasswordRequest {

    @Schema(description = "验证码")
    @NotNull(message = "验证码不能为空")
    private CaptchaRequest captchaRequest;

    @Schema(description = "新密码")
    @NotNull(message = "新密码不能为空")
    private String newPassword;

    @Schema(description = "登录账号")
    @NotNull(message = "登录账号不能为空")
    private String loginName;
}
