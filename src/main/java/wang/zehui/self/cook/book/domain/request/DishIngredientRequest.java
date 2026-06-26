package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author wangzehui
 * @Date 2026/6/24 14:06
 */
@Data
public class DishIngredientRequest {

    @Schema(description = "菜品食材id")
    private String id;

    @Schema(description = "菜品id")
    private String dishId;

    @Schema(description = "食材名称")
    private String ingredientName;

    @Schema(description = "食材规格 eg: 50g;5个")
    private String spec;
}
