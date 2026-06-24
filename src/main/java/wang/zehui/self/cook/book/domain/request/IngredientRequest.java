package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.CheckEnum;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.IngredientTypeEnum;

import javax.validation.constraints.NotBlank;

/**
 * @Author wangzehui
 * @Date 2026/6/24 10:52
 */
@Data
public class IngredientRequest {

    @Schema(description = "主键")
    private String id;

    @Schema(description = "食材名称")
    @NotBlank(message = "食材名称不能为空")
    private String ingredientName;

    @Schema(description = "食材图片")
    private String ingredientImageUrl;

    @Schema(description = "食材单位")
    @NotBlank(message = "食材单位不能为空")
    private String ingredientUnit;

    @SchemaEnum(value = IngredientTypeEnum.class, description = "食材类型")
    @CheckEnum(value = IngredientTypeEnum.class, message = "食材类型错误")
    private Integer type;
}
