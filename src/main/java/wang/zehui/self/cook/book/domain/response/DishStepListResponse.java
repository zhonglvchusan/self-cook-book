package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author wangzehui
 * @Date 2026/6/23 14:21
 */
@Data
public class DishStepListResponse {

    @Schema(description = "菜品步骤id")
    private String id;

    @Schema(description = "菜品id")
    private String dishId;

    @Schema(description = "步骤几")
    private Integer stepNumber;

    @Schema(description = "步骤标题")
    private String title;

    @Schema(description = "步骤内容")
    private String content;

    @Schema(description = "步骤图片")
    private String imageUrl;

    @Schema(description = "预计耗时(秒)")
    private Integer duration;

    @Schema(description = "小贴士")
    private String tip;
}
