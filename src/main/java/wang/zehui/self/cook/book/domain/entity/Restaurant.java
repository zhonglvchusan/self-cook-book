package wang.zehui.self.cook.book.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 餐厅信息表(Restaurant)表实体类
 *
 * @author wangzehui
 * @since 2026-06-12 14:06:05
 */
@Data
@TableName("t_restaurant")
public class Restaurant {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 餐厅名称
     */
    private String restaurantName;

    /**
     * 菜品数量
     */
    private Integer dishNumber;

    /**
     * 餐厅介绍
     */
    private String restaurantDescription;

    /**
     * 餐厅背景图地址
     */
    private String restaurantBackgroundUrl;

    /**
     * 店铺管理人员id
     */
    private String restaurantUserId;

    /**
     * 是否对外开放
     */
    private Boolean restaurantExternalFlag;

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

