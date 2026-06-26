package wang.zehui.self.cook.book.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 菜品食材表(DishIngredient)表实体类
 *
 * @author wangzehui
 * @since 2026-06-24 11:54:34
 */
@Data
@TableName("t_dish_ingredient")
public class DishIngredient {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 菜品id
     */
    private String dishId;

    /**
     * 食材id
     */
    private String ingredientId;

    /**
     * 所需数量
     */
    private BigDecimal amount;

    /**
     * 食材单位
     */
    private String unit;

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

