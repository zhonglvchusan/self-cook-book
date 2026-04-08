package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.domain.PageRequest;
import wang.zehui.self.cook.book.common.enums.GenderEnum;
import wang.zehui.self.cook.book.common.enums.UserAdminFlagEnum;

/**
 * @Author wangzehui
 * @Date 2026/4/2 15:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserListRequest extends PageRequest {

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "登录账号")
    private String loginName;

    @Schema(description = "昵称")
    private String nickname;

    @SchemaEnum(value = GenderEnum.class, description = "性别")
    private Integer gender;

    @Schema(description = "账号状态 0正常 1禁用")
    private Boolean state;

    @SchemaEnum(value = UserAdminFlagEnum.class, description = "管理员标识")
    private Integer adminFlag;

}
