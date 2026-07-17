package wang.zehui.self.cook.book.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.request.AddCommentRequest;
import wang.zehui.self.cook.book.domain.request.CommentSearchRequest;
import wang.zehui.self.cook.book.domain.request.MoreCommentRequest;
import wang.zehui.self.cook.book.domain.response.CommentListResponse;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.service.IRestaurantCommentService;

import javax.annotation.Resource;

/**
 * 餐厅评论表(RestaurantComment)表控制层
 *
 * @author wangzehui
 * @since 2026-07-16 11:49:21
 */
@Tag(name = "评论相关接口")
@RestController
@RequestMapping("/restaurant/comment")
public class ApiRestaurantCommentController {

    @Resource
    private IRestaurantCommentService restaurantCommentService;

    @PostMapping
    @Operation(summary = "添加评论 @author wangzh")
    public ResponseDTO<Boolean> addComment(@RequestBody @Validated AddCommentRequest request) {
        return ResponseDTO.success(restaurantCommentService.addComment(request));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "删除评论 @author wangzh")
    public ResponseDTO<Boolean> deleteComment(@PathVariable String commentId) {
        return ResponseDTO.success(restaurantCommentService.deleteComment(commentId));
    }

    @GetMapping
    @Operation(summary = "获取餐厅评论列表 @author wangzh")
    public ResponseDTO<PageResult<CommentListResponse>> getRestaurantComments(CommentSearchRequest searchRequest) {
        return ResponseDTO.success(restaurantCommentService.getRestaurantComments(searchRequest));
    }

    @GetMapping("/more")
    @Operation(summary = "查看更多回复 @author wangzh")
    public ResponseDTO<PageResult<CommentListResponse>> getMoreChildComment(MoreCommentRequest request) {
        return ResponseDTO.success(restaurantCommentService.getMoreChildComment(request));
    }

}

