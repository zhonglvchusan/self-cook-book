package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author wangzehui
 * @Date 2026/6/3 15:29
 */
@Data
public class RestaurantRequest {

    @Schema(description = "餐厅id 修改时传")
    private String id;

    @Schema(description = "餐厅名称")
    @NotBlank(message = "请输入餐厅名称")
    private String restaurantName;

    @Schema(description = "餐厅介绍")
    private String restaurantDescription;

    @Schema(description = "餐厅背景图地址")
    private String restaurantBackgroundUrl;

    @Schema(description = "是否对外开放")
    private Boolean restaurantExternalFlag;
}
