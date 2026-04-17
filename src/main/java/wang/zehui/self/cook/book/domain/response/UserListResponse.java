package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.GenderEnum;

import java.time.LocalDateTime;

/**
 * @Author wangzehui
 * @Date 2026/4/2 15:56
 */
@Data
public class UserListResponse {

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

    @Schema(description = "手机号")
    private String phoneNumber;

    @SchemaEnum(description = "性别", value = GenderEnum.class)
    private Integer gender;

    @Schema(description = "账号状态 0正常 1禁用")
    private Boolean state;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
