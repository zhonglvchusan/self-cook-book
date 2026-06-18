package wang.zehui.self.cook.book.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.entity.RestaurantCategory;
import wang.zehui.self.cook.book.domain.request.RestaurantCategoryRequest;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.domain.response.RestaurantCategoryListResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantCategorySearchRequest;
import wang.zehui.self.cook.book.service.IRestaurantCategoryService;

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
public class ApiRestaurantCategoryController {

    @Resource
    private IRestaurantCategoryService restaurantCategoryService;

    @PostMapping
    @Operation(summary = "新增/修改分类 @author wangzh")
    public ResponseDTO<Boolean> addOrUpdateCategory(@RequestBody @Validated RestaurantCategoryRequest request) {
        return ResponseDTO.success(restaurantCategoryService.addOrUpdateCategory(request));
    }

    @GetMapping
    @Operation(summary = "查询分类列表 @author wangzh")
    public ResponseDTO<PageResult<RestaurantCategoryListResponse>> getCategoryPageList(RestaurantCategorySearchRequest request) {
        return ResponseDTO.success(restaurantCategoryService.getCategoryPageList(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除分类 @author wangzh")
    public ResponseDTO<Boolean> deleteCategory(@PathVariable String id) {
        return ResponseDTO.success(restaurantCategoryService.deleteCategory(id));
    }
}

