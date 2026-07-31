package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.LaunchTypeEnum;

import java.time.LocalDateTime;

/**
 * @Author wangzehui
 * @Date 2026/7/30 15:28
 */
@Data
public class AdminDishListResponse {

    @Schema(description = "主键")
    private String id;

    @Schema(description = "餐厅id")
    private String restaurantId;

    @Schema(description = "餐厅名称")
    private String restaurantName;

    @Schema(description = "餐厅菜品分类id")
    private String restaurantCategoryId;

    @Schema(description = "餐厅菜品分类名称")
    private String restaurantCategoryName;

    @Schema(description = "菜品图片")
    private String dishImageUrl;

    @Schema(description = "菜品名称")
    private String dishName;

    @Schema(description = "菜品描述")
    private String dishDescription;

    @Schema(description = "菜品销量")
    private Integer dishSale;

    @Schema(description = "菜品评价等级")
    private Double dishRatingLevel;

    @Schema(description = "菜品价格(积分代替)")
    private Integer dishPrice;

    @SchemaEnum(value = LaunchTypeEnum.class, description = "上架状态")
    private Integer launchFlag;

    @Schema(description = "创建人id")
    private String createUserId;

    @Schema(description = "创建人名称")
    private String createUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
