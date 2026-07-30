package wang.zehui.self.cook.book.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户点赞表(UserLike)表实体类
 *
 * @author wangzehui
 * @since 2026-07-21 14:58:19
 */
@Data
@TableName("t_user_like")
public class UserLike {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 点赞的id
     */
    private String likeId;

    /**
     * 点赞id对应的类型 {@link wang.zehui.self.cook.book.common.enums.LikeTypeEnum}
     */
    private Integer likeType;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUserId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}

