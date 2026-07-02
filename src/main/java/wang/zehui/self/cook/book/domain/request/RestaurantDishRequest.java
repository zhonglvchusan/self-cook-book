package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author wangzehui
 * @Date 2026/6/22 14:33
 */
@Data
public class RestaurantDishRequest {

    @Schema(description = "菜品id")
    private String id;

    @Schema(description = "餐厅id")
    @NotBlank(message = "餐厅id不能为空")
    private String restaurantId;

    @Schema(description = "餐厅菜品分类id")
    @NotBlank(message = "餐厅菜品分类id不能为空")
    private String restaurantCategoryId;

    @Schema(description = "菜品图片")
    @NotBlank(message = "菜品图片不能为空")
    private String dishImageUrl;

    @Schema(description = "菜品名称")
    @NotBlank(message = "菜品名称不能为空")
    private String dishName;

    @Schema(description = "菜品描述")
    private String dishDescription;

    @Schema(description = "菜品价格")
    @NotNull(message = "菜品价格不能为空")
    private Integer dishPrice;

    @Schema(description = "菜品食材")
    private List<DishIngredientRequest> dishIngredientRequests;

    @Schema(description = "菜品步骤")
    private List<DishStepRequest> dishStepRequests;
}
