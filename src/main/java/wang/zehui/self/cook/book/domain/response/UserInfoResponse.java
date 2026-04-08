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
public class UserInfoResponse {

    @Schema(description = "用户id")
    private String id;


    @Schema(description = "微信openId")
    private String openId;

    @Schema(description = "微信unionId")
    private String unionId;

    @Schema(description = "登录账号")
    private String loginName;

    @Schema(description = "登录密码")
    private String loginPassword;

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

    @Schema(description = "账号状态")
    private Boolean state;

    @SchemaEnum(description = "管理员标识", value = UserAdminFlagEnum.class)
    private Integer adminFlag;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人id")
    private String createUserId;

    @Schema(description = "创建人名称")
    private String createUserName;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "更新人id")
    private String updateUserId;

    @Schema(description = "更新人名称")
    private String updateUserName;
}
