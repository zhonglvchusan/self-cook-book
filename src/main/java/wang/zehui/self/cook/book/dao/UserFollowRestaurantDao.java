package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wang.zehui.self.cook.book.domain.entity.UserFollowRestaurant;

/**
 * 用户关注餐厅表(UserFollowRestaurant)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-07-08 14:37:17
 */
@Mapper
public interface UserFollowRestaurantDao extends BaseMapper<UserFollowRestaurant> {
}

