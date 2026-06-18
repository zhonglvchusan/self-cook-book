package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.zehui.self.cook.book.common.domain.PageRequest;

/**
 * @Author wangzehui
 * @Date 2026/6/15 14:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RestaurantCategorySearchRequest extends PageRequest {

    @Schema(description = "餐厅id")
    private String restaurantId;
}
