package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.GenderEnum;
import wang.zehui.self.cook.book.common.enums.UserAdminFlagEnum;

import java.time.LocalDateTime;

/**
 * @Author wangzehui
 * @Date 2026/4/2 15:58
 */
@Data
public class ApiUserInfoResponse {

    @Schema(description = "用户id")
    private String id;

    @Schema(description = "登录账号")
    private String loginName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "真实姓名")
    private String realName;

    @SchemaEnum(description = "性别", value = GenderEnum.class)
    private Integer gender;

    @Schema(description = "手机号")
    private String phoneNumber;

    @Schema(description = "邮箱地址")
    private String email;
}
