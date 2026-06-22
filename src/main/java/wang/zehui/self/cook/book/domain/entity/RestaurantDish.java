package wang.zehui.self.cook.book.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 菜品分类菜品表(RestaurantDish)表实体类
 *
 * @author wangzehui
 * @since 2026-06-22 14:24:43
 */
@Data
@TableName("t_restaurant_dish")
public class RestaurantDish {

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
     * 餐厅菜品分类id
     */
    private String restaurantCategoryId;

    /**
     * 菜品图片
     */
    private String dishImageUrl;

    /**
     * 菜品名称
     */
    private String dishName;

    /**
     * 菜品销量
     */
    private Integer dishSale;

    /**
     * 菜品评价等级
     */
    private Double dishRatingLevel;

    /**
     * 菜品价格(积分代替)
     */
    private Integer dishPrice;

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

