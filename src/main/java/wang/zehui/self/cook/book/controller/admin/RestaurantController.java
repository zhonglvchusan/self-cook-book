package wang.zehui.self.cook.book.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.request.RestaurantSearchRequest;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.domain.response.RestaurantListResponse;
import wang.zehui.self.cook.book.service.IRestaurantService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 餐厅信息表(Restaurant)表控制层
 *
 * @author wangzehui
 * @since 2026-06-03 14:58:48
 */
@Tag(name = "餐厅管理相关接口")
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Resource
    private IRestaurantService restaurantService;

    @DeleteMapping("/{restaurantId}")
    @Operation(summary = "删除餐厅 @author wangzh")
    public ResponseDTO<Boolean> deleteRestaurant(@PathVariable String restaurantId) {
        return ResponseDTO.success(restaurantService.deleteRestaurant(restaurantId));
    }

    @GetMapping
    @Operation(summary = "获取餐厅分页列表 @author wangzh")
    public ResponseDTO<PageResult<RestaurantListResponse>> getRestaurantPageList(RestaurantSearchRequest request) {
        return ResponseDTO.success(restaurantService.getRestaurantPageList(request));
    }

}

