package wang.zehui.self.cook.book.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.request.RestaurantRequest;
import wang.zehui.self.cook.book.domain.request.RestaurantSearchRequest;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.domain.response.RestaurantListResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantResponse;
import wang.zehui.self.cook.book.service.IRestaurantService;

import javax.annotation.Resource;

/**
 * @Author wangzehui
 * @Date 2026/6/5 15:19
 */
@Tag(name = "小程序端餐厅接口")
@RestController
@RequestMapping("/restaurant")
public class ApiRestaurantController {

    @Resource
    private IRestaurantService restaurantService;

    @GetMapping
    @Operation(summary = "获取餐厅信息 @author wangzh")
    public ResponseDTO<RestaurantResponse> getRestaurantInfo(String restaurantId) {
        return ResponseDTO.success(restaurantService.getRestaurantInfo(restaurantId));
    }

    @PostMapping
    @Operation(summary = "添加/修改餐厅信息 @author wangzh")
    public ResponseDTO<Boolean> addOrUpdateRestaurant(@RequestBody @Validated RestaurantRequest request) {
        return ResponseDTO.success(restaurantService.addOrUpdateRestaurant(request));
    }

    @GetMapping("list")
    @Operation(summary = "获取餐厅分页列表 @author wangzh")
    public ResponseDTO<PageResult<RestaurantListResponse>> getRestaurantPageList(RestaurantSearchRequest request) {
        return ResponseDTO.success(restaurantService.getRestaurantPageList(request));
    }
}
