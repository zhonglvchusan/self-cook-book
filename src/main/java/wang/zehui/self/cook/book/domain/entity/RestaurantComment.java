package wang.zehui.self.cook.book.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 餐厅评论表(RestaurantComment)表实体类
 *
 * @author wangzehui
 * @since 2026-07-16 11:49:22
 */
@Data
@TableName("t_restaurant_comment")
public class RestaurantComment {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 餐厅id
     */
    private String restaurantId;

    /**
     * 菜品id
     */
    private String dishId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 评论类型 0: 评论 1: 回复
     */
    private Integer type;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likeNumber;

    /**
     * 关联id 回复的id
     */
    private String parentId;

    /**
     * 根评论id
     */
    private String rootId;

    /**
     * 是否删除
     */
    private Boolean deleted;

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

    /**
     * 查询剩余回复总数时使用
     */
    @TableField(exist = false)
    private Integer remainingCount;

}

