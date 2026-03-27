package wang.zehui.self.cook.book.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * (User)表实体类
 *
 * @author wangzehui
 * @since 2025-10-29 16:59:43
 */
@Data
@TableName("t_user")
public class User {


    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;


    /**
     * 微信openId
     */
    private String openId;

    /**
     * 微信unionId
     */
    private String unionId;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPassword;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别 0未知 1男 2女
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 账号状态 0正常 1禁用
     */
    private Boolean state;

    /**
     * 管理员标识 0超级管理员 1普通管理员 2用户
     */
    private Integer adminFlag;

    /**
     * 是否删除 0未删除 1已删除
     */
    private Boolean deleted;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人id
     */
    private String updateUserId;

}

