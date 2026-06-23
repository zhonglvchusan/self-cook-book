package wang.zehui.self.cook.book.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.request.RestaurantDishRequest;
import wang.zehui.self.cook.book.domain.request.RestaurantDishSearchRequest;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.domain.response.RestaurantDishInfoResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantDishListResponse;
import wang.zehui.self.cook.book.service.IRestaurantDishService;

import javax.annotation.Resource;

/**
 * 菜品分类菜品表(RestaurantDish)表控制层
 *
 * @author wangzehui
 * @since 2026-06-22 14:24:42
 */
@Tag(name = "餐厅下菜品相关接口")
@RestController
@RequestMapping("/restaurant/dish")
public class ApiRestaurantDishController {

    @Resource
    private IRestaurantDishService restaurantDishService;

    @Operation(summary = "新增/修改菜品信息 @author wangzh")
    @PostMapping
    public ResponseDTO<Boolean> addOrUpdateDish(@RequestBody @Validated RestaurantDishRequest request) {
        return ResponseDTO.success(restaurantDishService.addOrUpdateDish(request));
    }

    @Operation(summary = "删除菜品信息 @author wangzh")
    @DeleteMapping("/{id}")
    public ResponseDTO<Boolean> deleteDish(@PathVariable String id) {
        return ResponseDTO.success(restaurantDishService.deleteDish(id));
    }

    @Operation(summary = "获取菜品分页列表 @author wangzh")
    @GetMapping
    public ResponseDTO<PageResult<RestaurantDishListResponse>> getDishPageList(RestaurantDishSearchRequest request) {
        return ResponseDTO.success(restaurantDishService.getDishPageList(request));
    }

    @Operation(summary = "获取菜品详情 @author wangzh")
    @GetMapping("/{dishId}")
    public ResponseDTO<RestaurantDishInfoResponse> getDishInfo(@PathVariable String dishId) {
        return ResponseDTO.success(restaurantDishService.getDishInfo(dishId));
    }
}

