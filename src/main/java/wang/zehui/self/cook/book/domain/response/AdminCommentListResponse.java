package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author wangzehui
 * @Date 2026/7/30 14:43
 */
@Data
public class AdminCommentListResponse {

    @Schema(description = "主键")
    private String id;

    @Schema(description = "餐厅id")
    private String restaurantId;

    @Schema(description = "餐厅名")
    private String restaurantName;

    @Schema(description = "菜品id")
    private String dishId;

    @Schema(description = "菜品名称")
    private String dishName;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "评论类型 0: 评论 1: 回复")
    private Integer type;

    @Schema(description = "评价内容")
    private String content;

    @Schema(description = "点赞数")
    private Integer likeNumber;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
