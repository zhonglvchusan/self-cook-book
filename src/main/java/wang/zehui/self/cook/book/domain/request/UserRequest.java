package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.GenderEnum;

/**
 * @Author wangzehui
 * @Date 2025/10/28 15:27
 */
@Data
@Schema
public class UserRequest {

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "管理员标识 0: 超级管理员 1: 管理员 2: 普通用户")
    private Integer adminFlag;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    private String nickname;

    @SchemaEnum(value = GenderEnum.class, description = "性别")
    private Integer gender;

    @Schema(description = "绑定手机号")
    private String phoneNumber;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "是否是管理员")
    private Boolean isAdmin;
}
