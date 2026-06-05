package wang.zehui.self.cook.book.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wang.zehui.self.cook.book.domain.request.RestaurantRequest;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
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
    public ResponseDTO<RestaurantResponse> getRestaurantInfo() {
        return ResponseDTO.success(restaurantService.getRestaurantInfo(null));
    }

    @PostMapping
    @Operation(summary = "添加/修改餐厅信息 @author wangzh")
    public ResponseDTO<Boolean> addOrUpdateRestaurant(@RequestBody @Validated RestaurantRequest request) {
        return ResponseDTO.success(restaurantService.addOrUpdateRestaurant(request));
    }
}
