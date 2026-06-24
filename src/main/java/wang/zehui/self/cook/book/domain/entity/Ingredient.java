package wang.zehui.self.cook.book.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 食材表(Ingredient)表实体类
 *
 * @author wangzehui
 * @since 2026-06-24 10:55:09
 */
@Data
@TableName("t_ingredient")
public class Ingredient {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 食材名称
     */
    private String ingredientName;

    /**
     * 食材图片
     */
    private String ingredientImageUrl;

    /**
     * 食材单位
     */
    private String ingredientUnit;

    /**
     * 类型: 0: 系统食材 1: 用户个人食材
     */
    private Integer type;

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

