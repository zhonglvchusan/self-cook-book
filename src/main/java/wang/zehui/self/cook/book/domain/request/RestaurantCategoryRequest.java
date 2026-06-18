package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author wangzehui
 * @Date 2026/6/15 14:44
 */
@Data
public class RestaurantCategoryRequest {

    @Schema(description = "餐厅分类id")
    private String id;

    @Schema(description = "餐厅id")
    private String restaurantId;

    @Schema(description = "类别icon图片地址")
    private String categoryIcon;

    @Schema(description = "类别名称")
    @NotBlank(message = "类别名称不能为空")
    private String categoryName;

    @Schema(description = "排序")
    private Integer sort;
}
