package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.CheckEnum;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.IngredientTypeEnum;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "食材名称不能为空")
    private String ingredientName;

    @Schema(description = "食材规格 eg: 50g;5个")
    @NotBlank(message = "食材规格不能为空")
    private String spec;

    @Schema(description = "食材图片")
    private String ingredientImageUrl;

    @SchemaEnum(value = IngredientTypeEnum.class, description = "食材类型")
    @CheckEnum(value = IngredientTypeEnum.class, message = "食材类型错误")
    private Integer type;
}
