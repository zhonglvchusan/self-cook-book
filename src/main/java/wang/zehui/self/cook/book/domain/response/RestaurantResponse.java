package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author wangzehui
 * @Date 2026/6/3 15:12
 */
@Data
public class RestaurantResponse {

    @Schema(description = "餐厅id")
    private String id;

    @Schema(description = "餐厅名称")
    private String restaurantName;

    @Schema(description = "菜品数量")
    private Integer dishNumber;

    @Schema(description = "餐厅介绍")
    private String restaurantDescription;

    @Schema(description = "餐厅背景图地址")
    private String restaurantBackgroundUrl;

    @Schema(description = "是否对外开放")
    private Boolean restaurantExternalFlag;

    @Schema(description = "是否是餐厅管理员")
    private Boolean adminFlag;
}
