package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.zehui.self.cook.book.common.domain.PageRequest;

/**
 * @Author wangzehui
 * @Date 2026/7/16 14:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommentSearchRequest extends PageRequest {

    @Schema(description = "餐厅id")
    private String restaurantId;

    @Schema(description = "菜品id")
    private String dishId;
}
