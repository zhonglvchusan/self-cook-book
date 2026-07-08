package wang.zehui.self.cook.book.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.service.IUserFollowRestaurantService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户关注餐厅表(UserFollowRestaurant)表控制层
 *
 * @author wangzehui
 * @since 2026-07-08 14:37:17
 */
@Tag(name = "关注餐厅相关接口")
@RestController
@RequestMapping("/user/follow/restaurant")
public class ApiUserFollowRestaurantController {

    @Resource
    private IUserFollowRestaurantService userFollowRestaurantService;

    @PostMapping("/{restaurantId}")
    @Operation(summary = "关注餐厅 @author wangzh")
    public ResponseDTO<Boolean> followRestaurant(@PathVariable String restaurantId) {
        return ResponseDTO.success(userFollowRestaurantService.followRestaurant(restaurantId));
    }

    @DeleteMapping("/{restaurantId}")
    @Operation(summary = "取消关注餐厅 @author wangzh")
    public ResponseDTO<Boolean> cancelFollowRestaurant(@PathVariable String restaurantId) {
        return ResponseDTO.success(userFollowRestaurantService.cancelFollowRestaurant(restaurantId));
    }

    @GetMapping("/{restaurantId}")
    @Operation(summary = "判断用户是否关注餐厅 @author wangzh")
    public ResponseDTO<Boolean> isFollowRestaurant(@PathVariable String restaurantId) {
        return ResponseDTO.success(userFollowRestaurantService.isFollowRestaurant(restaurantId));
    }

}

