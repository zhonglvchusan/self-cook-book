package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.domain.PageRequest;
import wang.zehui.self.cook.book.common.enums.IngredientTypeEnum;

/**
 * @Author wangzehui
 * @Date 2026/6/24 10:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IngredientSearchRequest extends PageRequest {

    @Schema(description = "食材名称")
    private String ingredientName;

    @SchemaEnum(value = IngredientTypeEnum.class, description = "食材类型")
    private Integer type;
}
