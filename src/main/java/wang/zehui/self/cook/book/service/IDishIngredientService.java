package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.domain.entity.DishIngredient;
import wang.zehui.self.cook.book.domain.request.DishIngredientRequest;
import wang.zehui.self.cook.book.domain.response.DishIngredientResponse;

import java.util.List;

/**
 * 菜品食材表(DishIngredient)表服务接口
 *
 * @author wangzehui
 * @since 2026-06-24 11:54:34
 */
public interface IDishIngredientService extends IService<DishIngredient> {

    /**
     * @Description: 新增/修改菜品食材
     * @param dishIngredientRequests 菜品食材信息
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/6/24 14:03
     */
    Boolean addOrUpdateDishIngredientBatch(List<DishIngredientRequest> dishIngredientRequests);

    /**
     * @Description: 获取菜品食材
     * @param dishId 菜品id
     * @Return: java.util.List<wang.zehui.self.cook.book.domain.response.DishIngredientResponse>
     * @Author: wangzehui
     * @Date: 2026/6/24 14:04
     */
    List<DishIngredientResponse> getDishIngredients(String dishId);
}

