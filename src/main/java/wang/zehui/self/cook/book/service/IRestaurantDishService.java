package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.entity.RestaurantDish;
import wang.zehui.self.cook.book.domain.request.RestaurantDishRequest;
import wang.zehui.self.cook.book.domain.request.RestaurantDishSearchRequest;
import wang.zehui.self.cook.book.domain.response.RestaurantDishInfoResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantDishListResponse;

/**
 * 菜品分类菜品表(RestaurantDish)表服务接口
 *
 * @author wangzehui
 * @since 2026-06-22 14:24:43
 */
public interface IRestaurantDishService extends IService<RestaurantDish> {

    /**
     * @Description: 新增/修改菜品信息
     * @param request 菜品信息
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/6/22 14:33
     */
    Boolean addOrUpdateDish(RestaurantDishRequest request);

    /**
     * @Description: 删除菜品信息
     * @param id 菜品id
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/6/22 14:34
     */
    Boolean deleteDish(String id);

    /**
     * @Description: 获取菜品分页列表
     * @param request 菜品搜索参数
     * @Return: wang.zehui.self.cook.book.common.domain.PageResult<wang.zehui.self.cook.book.domain.request.RestaurantDishSearchRequest>
     * @Author: wangzehui
     * @Date: 2026/6/22 14:37
     */
    PageResult<RestaurantDishListResponse> getDishPageList(RestaurantDishSearchRequest request);

    /**
     * @Description: 获取菜品详情
     * @param dishId 菜品id
     * @Return: wang.zehui.self.cook.book.domain.response.RestaurantDishInfoResponse
     * @Author: wangzehui
     * @Date: 2026/6/23 14:52
     */
    RestaurantDishInfoResponse getDishInfo(String dishId);
}

