package wang.zehui.self.cook.book.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 菜品步骤表(DishStep)表实体类
 *
 * @author wangzehui
 * @since 2026-06-23 14:18:10
 */
@Data
@TableName("t_dish_step")
public class DishStep {

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
     * 步骤几
     */
    private Integer stepNumber;

    /**
     * 步骤标题
     */
    private String title;

    /**
     * 步骤内容
     */
    private String content;

    /**
     * 步骤图片
     */
    private String imageUrl;

    /**
     * 预计耗时(秒)
     */
    private Integer duration;

    /**
     * 小贴士
     */
    private String tip;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUserId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}

