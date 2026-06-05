package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.LoginDeviceEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author wangzehui
 * @Date 2026/6/5 11:09
 */
@Data
public class WxPhoneLoginRequest {

    @SchemaEnum(value = LoginDeviceEnum.class, description = "登录设备枚举", required = true)
    @NotNull(message = "登录设备类型不能为空")
    private Integer loginDevice;

    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String loginName;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "验证码")
    @NotNull(message = "验证码不能为空")
    private CaptchaRequest captchaRequest;
}
