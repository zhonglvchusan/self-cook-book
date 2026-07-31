package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.entity.RestaurantDish;
import wang.zehui.self.cook.book.domain.request.AdminDishSearchRequest;
import wang.zehui.self.cook.book.domain.request.RestaurantDishRequest;
import wang.zehui.self.cook.book.domain.request.RestaurantDishSearchRequest;
import wang.zehui.self.cook.book.domain.response.AdminDishListResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantDishConfigListResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantDishInfoResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantDishListResponse;

import java.util.List;
import java.util.Map;

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

    /**
     * @Description: 通过id获取菜品信息
     * @param dishIds 菜品id
     * @Return: java.util.Map<java.lang.String,wang.zehui.self.cook.book.domain.entity.RestaurantDish>
     * @Author: wangzehui
     * @Date: 2026/7/17 10:27
     */
    Map<String, RestaurantDish> getDishMapByIds(List<String> dishIds);

    /**
     * @Description: 更新菜品上架状态
     * @param dishId 菜品id
     * @param launchType {@link wang.zehui.self.cook.book.common.enums.LaunchTypeEnum}
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/7/31 9:51
     */
    Boolean updateDishLaunchStatus(String dishId, Integer launchType);

    /**
     * @Description: 获取管理菜品时的菜品信息
     * @param request 菜品搜索参数
     * @Return: wang.zehui.self.cook.book.common.domain.PageResult<wang.zehui.self.cook.book.domain.response.RestaurantDishConfigListResponse>
     * @Author: wangzehui
     * @Date: 2026/7/31 15:08
     */
    PageResult<RestaurantDishConfigListResponse> getDishConfigPageList(RestaurantDishSearchRequest request);

    /******************************************** 以下为后台方法 **************************************/

    /**
     * @Description: 获取平台餐品列表
     * @param request 搜索参数
     * @Return: wang.zehui.self.cook.book.common.domain.PageResult<wang.zehui.self.cook.book.domain.response.AdminDishListResponse>
     * @Author: wangzehui
     * @Date: 2026/7/30 15:32
     */
    PageResult<AdminDishListResponse> getAdminDishPageList(AdminDishSearchRequest request);

}

