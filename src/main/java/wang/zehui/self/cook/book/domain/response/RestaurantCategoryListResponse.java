package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author wangzehui
 * @Date 2026/6/15 14:47
 */
@Data
public class RestaurantCategoryListResponse {

    @Schema(description = "餐厅分类id")
    private String id;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "分类图标")
    private String categoryIcon;

    @Schema(description = "排序")
    private Integer sort;
}
