package wang.zehui.self.cook.book.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.domain.response.RestaurantCategoryListResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantCategorySearchRequest;
import wang.zehui.self.cook.book.service.IRestaurantCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 餐厅分类表(RestaurantCategory)表控制层
 *
 * @author wangzehui
 * @since 2026-06-15 14:15:01
 */
@Tag(name = "餐厅分类管理相关接口")
@RestController
@RequestMapping("/restaurant/category")
public class RestaurantCategoryController {

    @Resource
    private IRestaurantCategoryService restaurantCategoryService;

    @GetMapping
    @Operation(summary = "查询分类列表 @author wangzh")
    public ResponseDTO<PageResult<RestaurantCategoryListResponse>> getCategoryPageList(RestaurantCategorySearchRequest request) {
        return ResponseDTO.success(restaurantCategoryService.getCategoryPageList(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除分类 @author wangzh")
    @SaCheckPermission("restaurant:category:delete")
    public ResponseDTO<Boolean> deleteCategory(@PathVariable String id) {
        return ResponseDTO.success(restaurantCategoryService.deleteCategory(id));
    }
}

