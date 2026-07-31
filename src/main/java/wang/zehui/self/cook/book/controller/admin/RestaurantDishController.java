package wang.zehui.self.cook.book.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.request.AdminDishSearchRequest;
import wang.zehui.self.cook.book.domain.request.DishLaunchRequest;
import wang.zehui.self.cook.book.domain.response.AdminDishListResponse;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.domain.response.RestaurantDishInfoResponse;
import wang.zehui.self.cook.book.service.IRestaurantDishService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 菜品分类菜品表(RestaurantDish)表控制层
 *
 * @author wangzehui
 * @since 2026-06-22 14:24:42
 */
@Tag(name = "餐厅菜品管理相关接口")
@RestController
@RequestMapping("/restaurant/dish")
public class RestaurantDishController {

    @Resource
    private IRestaurantDishService restaurantDishService;

    @GetMapping
    @Operation(summary = "获取平台餐品列表 @author wangzh")
    public ResponseDTO<PageResult<AdminDishListResponse>> getAdminDishPageList(AdminDishSearchRequest request) {
        return ResponseDTO.success(restaurantDishService.getAdminDishPageList(request));
    }

    @GetMapping("/{dishId}")
    @Operation(summary = "获取菜品详情 @author wangzh")
    @SaCheckPermission("restaurant:dish:info")
    public ResponseDTO<RestaurantDishInfoResponse> getDishInfo(@PathVariable String dishId) {
        return ResponseDTO.success(restaurantDishService.getDishInfo(dishId));
    }

    @PutMapping
    @Operation(summary = "更新菜品上架状态 @author wangzh")
    @SaCheckPermission("restaurant:dish:update")
    public ResponseDTO<Boolean> updateDishLaunchStatus(@RequestBody @Validated DishLaunchRequest request) {
        return ResponseDTO.success(restaurantDishService.updateDishLaunchStatus(request.getDishId(), request.getLaunchType()));
    }

}

