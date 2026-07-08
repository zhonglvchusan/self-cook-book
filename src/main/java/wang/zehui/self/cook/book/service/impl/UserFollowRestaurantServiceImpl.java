package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import wang.zehui.self.cook.book.common.utils.RequestUtil;
import wang.zehui.self.cook.book.dao.UserFollowRestaurantDao;
import wang.zehui.self.cook.book.domain.entity.UserFollowRestaurant;
import wang.zehui.self.cook.book.service.IUserFollowRestaurantService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 用户关注餐厅表(UserFollowRestaurant)表服务实现类
 *
 * @author wangzehui
 * @since 2026-07-08 14:37:17
 */
@Service
public class UserFollowRestaurantServiceImpl extends ServiceImpl<UserFollowRestaurantDao, UserFollowRestaurant> implements IUserFollowRestaurantService {

    @Override
    public Boolean followRestaurant(String restaurantId) {
        String userId = RequestUtil.getUserId();
        if (Objects.isNull(userId)) {
            return false;
        }

        // 判断是否已关注
        if (this.isFollowRestaurant(restaurantId)) {
            return true;
        }

        UserFollowRestaurant userFollowRestaurant = new UserFollowRestaurant();
        userFollowRestaurant.setUserId(userId);
        userFollowRestaurant.setRestaurantId(restaurantId);

        return this.save(userFollowRestaurant);
    }

    @Override
    public Boolean cancelFollowRestaurant(String restaurantId) {
        String userId = RequestUtil.getUserId();
        if (Objects.isNull(userId)) {
            return true;
        }

        // 判断是否已关注
        if (!this.isFollowRestaurant(restaurantId)) {
            return true;
        }

        return this.remove(Wrappers.<UserFollowRestaurant>lambdaQuery()
                .eq(UserFollowRestaurant::getUserId, userId)
                .eq(UserFollowRestaurant::getRestaurantId, restaurantId));
    }

    @Override
    public Boolean isFollowRestaurant(String restaurantId) {
        String userId = RequestUtil.getUserId();
        if (Objects.isNull(userId)) {
            return false;
        }

        return this.count(Wrappers.<UserFollowRestaurant>lambdaQuery()
                .eq(UserFollowRestaurant::getUserId, userId)
                .eq(UserFollowRestaurant::getRestaurantId, restaurantId)) > 0;
    }
}

