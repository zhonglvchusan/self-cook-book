package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.CommentTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author wangzehui
 * @Date 2026/7/16 14:22
 */
@Data
public class CommentListResponse {

    @Schema(description = "评论id")
    private String id;

    @Schema(description = "餐厅id")
    private String restaurantId;

    @Schema(description = "菜谱id")
    private String dishId;

    @Schema(description = "菜谱名称")
    private String dishName;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "回复用户id")
    private String replyUserId;

    @Schema(description = "回复用户名称")
    private String replyUserName;

    @SchemaEnum(value = CommentTypeEnum.class, description = "评论类型")
    private Integer type;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "点赞数")
    private Integer likeNumber;

    @Schema(description = "回复评论id")
    private String parentId;

    @Schema(description = "根评论id")
    private String rootId;

    @Schema(description = "评论时间")
    private LocalDateTime createTime;

    @Schema(description = "子评论列表")
    private List<CommentListResponse> childrenReplies;

    @Schema(description = "查看剩余xx条回复")
    private Integer remainingCount;

    @Schema(description = "当前用户是否点赞")
    private Boolean likeStatus;
}
