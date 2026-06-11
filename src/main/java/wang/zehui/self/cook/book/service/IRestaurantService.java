package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.entity.Restaurant;
import wang.zehui.self.cook.book.domain.request.RestaurantRequest;
import wang.zehui.self.cook.book.domain.request.RestaurantSearchRequest;
import wang.zehui.self.cook.book.domain.response.RestaurantListResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantResponse;

/**
 * 餐厅信息表(Restaurant)表服务接口
 *
 * @author wangzehui
 * @since 2026-06-03 14:58:48
 */
public interface IRestaurantService extends IService<Restaurant> {

    /**
     * @Description: 获取餐厅基本信息
     * @param restaurantId 餐厅id
     * @Return: wang.zehui.self.cook.book.domain.response.RestaurantResponse
     * @Author: wangzehui
     * @Date: 2026/6/3 15:13
     */
    RestaurantResponse getRestaurantInfo(String restaurantId);

    /**
     * @Description: 通过用户id获取用户的餐厅
     * @param userId 用户id
     * @Return: wang.zehui.self.cook.book.domain.entity.Restaurant
     * @Author: wangzehui
     * @Date: 2026/6/3 15:36
     */
    Restaurant getRestaurantByUserId(String userId);

    /**
     * @Description: 新增/修改餐厅基础信息
     * @param request 请求参数
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/6/3 15:32
     */
    Boolean addOrUpdateRestaurant(RestaurantRequest request);

    /**
     * @Description: 删除餐厅
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/6/3 15:32
     */
    Boolean deleteRestaurant(String restaurantId);

    /**
     * @Description: 获取餐厅分页列表
     * @param request 查询参数
     * @Return: wang.zehui.self.cook.book.common.domain.PageResult<wang.zehui.self.cook.book.domain.response.RestaurantListResponse>
     * @Author: wangzehui
     * @Date: 2026/6/8 11:19
     */
    PageResult<RestaurantListResponse> getRestaurantPageList(RestaurantSearchRequest request);
}

