package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.domain.entity.UserFollowRestaurant;

/**
 * 用户关注餐厅表(UserFollowRestaurant)表服务接口
 *
 * @author wangzehui
 * @since 2026-07-08 14:37:17
 */
public interface IUserFollowRestaurantService extends IService<UserFollowRestaurant> {

    /**
     * @Description: 关注餐厅
     * @param restaurantId 餐厅id
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/7/8 15:22
     */
    Boolean followRestaurant(String restaurantId);

    /**
     * @Description: 取消关注餐厅
     * @param restaurantId 餐厅id
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/7/8 15:27
     */
    Boolean cancelFollowRestaurant(String restaurantId);

    /**
     * @Description: 判断用户是否关注餐厅
     * @param restaurantId 餐厅id
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/7/8 15:27
     */
    Boolean isFollowRestaurant(String restaurantId);
}

