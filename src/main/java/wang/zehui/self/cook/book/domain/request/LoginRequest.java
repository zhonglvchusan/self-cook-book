package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.LoginDeviceEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author wangzehui
 * @Date 2025/11/6 12:00
 */
@Data
public class LoginRequest {

    @Schema(description = "是否小程序用户", hidden = true)
    private Boolean miniAppFlag = false;

    @SchemaEnum(value = LoginDeviceEnum.class, description = "登录设备枚举", required = true)
    @NotNull(message = "登录设备类型不能为空")
    private Integer loginDevice;

    @Schema(description = "登录名")
    @NotBlank(message = "登录名不能为空", groups = { AdminGroup.class})
    private String loginName;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空", groups = { AdminGroup.class})
    private String password;

    @Schema(description = "验证码")
    @NotNull(message = "验证码不能为空", groups = { AdminGroup.class })
    private CaptchaRequest captchaRequest;

    @Schema(description = "微信小程序返回的授权手机号code")
    @NotBlank(message = "微信小程序授权手机号code不能为空", groups = { ApiGroup.class })
    private String phoneCode;

    @Schema(description = "微信小程序登录code")
    @NotBlank(message = "微信小程序登录code不能为空", groups = { ApiGroup.class })
    private String loginCode;

    public static class AdminGroup {}

    public static class ApiGroup {}
}
