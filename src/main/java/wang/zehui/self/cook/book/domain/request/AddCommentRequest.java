package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author wangzehui
 * @Date 2026/7/16 11:54
 */
@Data
public class AddCommentRequest {

    @Schema(description = "餐厅id")
    @NotBlank(message = "餐厅id不能为空")
    private String restaurantId;

    @Schema(description = "菜品id")
    @NotBlank(message = "菜品id不能为空")
    private String dishId;

    @Schema(description = "评论内容")
    @NotBlank(message = "评论内容不能为空")
    private String content;

    @Schema(description = "回复的id")
    private String parentId;

    @Schema(description = "根评论id")
    private String rootId;
}
