package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.LoginDeviceEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author wangzehui
 * @Date 2026/6/4 10:03
 */
@Data
public class WxLoginRequest {

    @SchemaEnum(value = LoginDeviceEnum.class, description = "登录设备枚举", required = true)
    @NotNull(message = "登录设备类型不能为空")
    private Integer loginDevice;

    @Schema(description = "微信小程序登录code")
    @NotBlank(message = "微信小程序登录code不能为空")
    private String loginCode;
}
