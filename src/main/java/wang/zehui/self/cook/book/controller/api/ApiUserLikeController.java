package wang.zehui.self.cook.book.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import wang.zehui.self.cook.book.common.utils.RequestUtil;
import wang.zehui.self.cook.book.domain.request.LikeRequest;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.service.IUserLikeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户点赞表(UserLike)表控制层
 *
 * @author wangzehui
 * @since 2026-07-21 14:58:19
 */
@Tag(name = "用户点赞相关接口")
@RestController
@RequestMapping("/user/like")
public class ApiUserLikeController {

    @Resource
    private IUserLikeService userLikeService;

    @PostMapping
    @Operation(summary = "添加点赞 @author wangzh")
    public ResponseDTO<Boolean> addLike(@RequestBody @Validated LikeRequest request) {
        request.setUserId(RequestUtil.getUserId());
        return ResponseDTO.success(userLikeService.addLike(request));
    }

    @DeleteMapping
    @Operation(summary = "删除点赞 @author wangzh")
    public ResponseDTO<Boolean> removeLike(@RequestBody @Validated LikeRequest request) {
        request.setUserId(RequestUtil.getUserId());
        return ResponseDTO.success(userLikeService.removeLike(request));
    }

}

