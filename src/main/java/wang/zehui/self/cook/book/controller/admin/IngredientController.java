package wang.zehui.self.cook.book.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.entity.Ingredient;
import wang.zehui.self.cook.book.domain.request.IngredientRequest;
import wang.zehui.self.cook.book.domain.request.IngredientSearchRequest;
import wang.zehui.self.cook.book.domain.response.IngredientListResponse;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.service.IIngredientService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 食材表(Ingredient)表控制层
 *
 * @author wangzehui
 * @since 2026-06-24 10:51:24
 */
@Tag(name = "食材管理相关接口")
@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Resource
    private IIngredientService ingredientService;

    @Operation(summary = "新增/修改食材信息 @author wangzh")
    @PostMapping
    @SaCheckPermission("ingredient:addOrUpdate")
    public ResponseDTO<Boolean> addOrUpdateIngredient(@RequestBody @Validated IngredientRequest request) {
        List<Ingredient> ingredients = ingredientService.addOrUpdateIngredientBatch(Collections.singletonList(request));
        if (CollectionUtils.isEmpty(ingredients)) {
            return ResponseDTO.error();
        } else {
            return ResponseDTO.success();
        }
    }

    @Operation(summary = "删除食材信息 @author wangzh")
    @DeleteMapping("/{ingredientId}")
    @SaCheckPermission("ingredient:delete")
    public ResponseDTO<Boolean> deleteIngredient(@PathVariable String ingredientId) {
        return ResponseDTO.success(ingredientService.deleteIngredient(ingredientId));
    }

    @Operation(summary = "查询食材分页列表 @author wangzh")
    @GetMapping
    public ResponseDTO<PageResult<IngredientListResponse>> getIngredientPageList(IngredientSearchRequest request) {
        PageResult<IngredientListResponse> pageResult = ingredientService.getIngredientPageList(request);
        return ResponseDTO.success(pageResult);
    }

}

