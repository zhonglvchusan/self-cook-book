package wang.zehui.self.cook.book.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户关注餐厅表(UserFollowRestaurant)表实体类
 *
 * @author wangzehui
 * @since 2026-07-08 14:37:17
 */
@Data
@TableName("t_user_follow_restaurant")
public class UserFollowRestaurant {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 餐厅id
     */
    private String restaurantId;

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

