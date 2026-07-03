package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author wangzehui
 * @Date 2026/6/23 14:52
 */
@Data
public class RestaurantDishInfoResponse {

    @Schema(description = "菜品id")
    private String id;

    @Schema(description = "餐厅id")
    private String restaurantId;

    @Schema(description = "餐厅菜品分类id")
    private String restaurantCategoryId;

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

    @Schema(description = "菜品价格")
    private Integer dishPrice;

    @Schema(description = "菜品食材")
    private List<DishIngredientResponse> dishIngredients;

    @Schema(description = "菜品步骤")
    private List<DishStepListResponse> dishSteps;

}
