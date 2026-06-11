package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author wangzehui
 * @Date 2026/6/8 11:14
 */
@Data
public class RestaurantListResponse {

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

    @Schema(description = "店铺管理人员id")
    private String restaurantUserId;

    @Schema(description = "店铺管理人员名称")
    private String restaurantUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
