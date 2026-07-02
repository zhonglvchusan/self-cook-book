package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.entity.Ingredient;
import wang.zehui.self.cook.book.domain.request.IngredientRequest;
import wang.zehui.self.cook.book.domain.request.IngredientSearchRequest;
import wang.zehui.self.cook.book.domain.response.IngredientListResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 食材表(Ingredient)表服务接口
 *
 * @author wangzehui
 * @since 2026-06-24 10:51:24
 */
public interface IIngredientService extends IService<Ingredient> {

    /**
     * @Description: 批量新增/修改食材信息
     *  该方法返回值为保存的食材信息，若不需要返回给用户自行处理
     * @param requests 食材信息
     * @Return: java.util.List<wang.zehui.self.cook.book.domain.entity.Ingredient> 保存后的食材信息
     * @Author: wangzehui
     * @Date: 2026/6/24 11:18
     */
    List<Ingredient> addOrUpdateIngredientBatch(List<IngredientRequest> requests);

    /**
     * @Description: 删除食材信息
     * @param ingredientId 食材id
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/6/24 10:54
     */
    Boolean deleteIngredient(String ingredientId);

    /**
     * @Description: 查询食材分页列表
     * @param request 查询参数
     * @Return: wang.zehui.self.cook.book.common.domain.PageResult<wang.zehui.self.cook.book.domain.response.IngredientListResponse>
     * @Author: wangzehui
     * @Date: 2026/6/24 10:59
     */
    PageResult<IngredientListResponse> getIngredientPageList(IngredientSearchRequest request);

    /**
     * @Description: 根据食材名称列表，获取食材名称对应的id
     * @param ingredientNames 食材名称列表
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     * @Author: wangzehui
     * @Date: 2026/6/29 12:26
     */
    Map<String, String> getIdMapByIngredientNames(Set<String> ingredientNames);

    /**
     * @Description: 获取食材信息
     * @param ingredientIds 食材id列表
     * @Return: java.util.Map<java.lang.String,wang.zehui.self.cook.book.domain.entity.Ingredient>
     * @Author: wangzehui
     * @Date: 2026/7/2 10:45
     */
    Map<String, Ingredient> getIngredientMapByIds(Set<String> ingredientIds);
}

