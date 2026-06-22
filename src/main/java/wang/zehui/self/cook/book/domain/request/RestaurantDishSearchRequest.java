package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.zehui.self.cook.book.common.domain.PageRequest;

/**
 * @Author wangzehui
 * @Date 2026/6/22 14:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RestaurantDishSearchRequest extends PageRequest {

    @Schema(description = "餐厅分类id")
    private String restaurantCategoryId;

    @Schema(description = "菜品名称")
    private String dishName;

}
