package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.zehui.self.cook.book.common.domain.PageRequest;

/**
 * @Author wangzehui
 * @Date 2026/6/8 11:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RestaurantSearchRequest extends PageRequest {

    @Schema(description = "餐厅id")
    private String id;

    @Schema(description = "餐厅名称")
    private String restaurantName;

    @Schema(description = "餐厅介绍")
    private String restaurantDescription;
}
