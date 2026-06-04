package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.CheckEnum;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.GenderEnum;

/**
 * @Author wangzehui
 * @Date 2026/6/4 15:34
 */
@Data
public class ApiUserUpdateRequest {

    @Schema(description = "登录账号")
    private String loginName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "真实姓名")
    private String realName;

    @SchemaEnum(description = "性别", value = GenderEnum.class)
    @CheckEnum(message = "性别参数错误", value = GenderEnum.class)
    private Integer gender;

    @Schema(description = "手机号")
    private String phoneNumber;

    @Schema(description = "邮箱地址")
    private String email;
}
