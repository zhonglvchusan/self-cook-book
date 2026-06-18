package wang.zehui.self.cook.book.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 餐厅分类表(RestaurantCategory)表实体类
 *
 * @author wangzehui
 * @since 2026-06-15 14:48:44
 */
@Data
@TableName("t_restaurant_category")
public class RestaurantCategory {

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
     * 类别icon图片地址
     */
    private String categoryIcon;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 排序
     */
    private Integer sort;

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

}

