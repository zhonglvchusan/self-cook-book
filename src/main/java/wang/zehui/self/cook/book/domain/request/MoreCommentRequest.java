package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.zehui.self.cook.book.common.domain.PageRequest;

/**
 * @Author wangzehui
 * @Date 2026/7/17 11:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MoreCommentRequest extends PageRequest {

    @Schema(description = "评论id")
    private String commentId;
}
