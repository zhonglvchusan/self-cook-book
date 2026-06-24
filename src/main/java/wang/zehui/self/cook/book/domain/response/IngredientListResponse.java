package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.IngredientTypeEnum;

/**
 * @Author wangzehui
 * @Date 2026/6/24 10:58
 */
@Data
public class IngredientListResponse {

    @Schema(description = "主键")
    private String id;

    @Schema(description = "食材名称")
    private String ingredientName;

    @Schema(description = "食材图片")
    private String ingredientImageUrl;

    @Schema(description = "食材单位")
    private String ingredientUnit;

    @SchemaEnum(value = IngredientTypeEnum.class, description = "食材类型")
    private Integer type;
}
