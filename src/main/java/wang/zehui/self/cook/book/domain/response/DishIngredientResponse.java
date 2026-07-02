package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.IngredientTypeEnum;

/**
 * @Author wangzehui
 * @Date 2026/6/24 14:03
 */
@Data
public class DishIngredientResponse {

    @Schema(description = "菜品食材id")
    private String id;

    @Schema(description = "菜品id")
    private String dishId;

    @Schema(description = "食材名称")
    private String ingredientName;

    @Schema(description = "食材规格 eg: 50g;5个")
    private String spec;

    @Schema(description = "食材图片")
    private String ingredientImageUrl;

    @SchemaEnum(value = IngredientTypeEnum.class, description = "食材类型")
    private Integer type;

}
