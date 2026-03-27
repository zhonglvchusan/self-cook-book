package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author wangzehui
 * @Date 2026/3/26 17:05
 */
@Data
public class UserAddRequest {

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

    @Schema(description = "性别 0未知 1男 2女")
    private Integer gender;

    @Schema(description = "手机号")
    private String phoneNumber;

    @Schema(description = "邮箱地址")
    private String email;

    @Schema(description = "账号状态 0正常 1禁用")
    private Boolean state;

    @Schema(description = "管理员标识 0超级管理员 1普通管理员 2用户")
    private Integer adminFlag;

    @Schema(description = "是否删除 0未删除 1已删除")
    private Boolean deleted;

    @Schema(description = "备注")
    private String remark;
}
