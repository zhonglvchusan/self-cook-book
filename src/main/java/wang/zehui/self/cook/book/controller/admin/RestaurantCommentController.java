package wang.zehui.self.cook.book.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.request.AdminCommentSearchRequest;
import wang.zehui.self.cook.book.domain.response.AdminCommentListResponse;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.service.IRestaurantCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 餐厅评论表(RestaurantComment)表控制层
 *
 * @author wangzehui
 * @since 2026-07-16 11:49:21
 */
@Tag(name = "餐厅评论管理相关")
@RestController
@RequestMapping("/restaurant/comment")
public class RestaurantCommentController {

    @Resource
    private IRestaurantCommentService restaurantCommentService;

    @GetMapping
    @Operation(summary = "获取评论列表 @author wangzh")
    public ResponseDTO<PageResult<AdminCommentListResponse>> getAdminCommentList(AdminCommentSearchRequest request) {
        return ResponseDTO.success(restaurantCommentService.getAdminCommentList(request));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "删除评论 @author wangzh")
    @SaCheckPermission("restaurant:comment:delete")
    public ResponseDTO<Boolean> deleteComment(@PathVariable String commentId) {
        return ResponseDTO.success(restaurantCommentService.deleteComment(commentId));
    }
}

