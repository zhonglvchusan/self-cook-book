package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wang.zehui.self.cook.book.domain.entity.Restaurant;

/**
 * 餐厅信息表(Restaurant)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-06-03 14:58:48
 */
@Mapper
public interface RestaurantDao extends BaseMapper<Restaurant> {

    /**
     * @Description: 更新菜品数量
     * @param restaurantId 餐厅id
     * @param operation 增加/删除
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/6/22 15:07
     */
    Boolean updateDishNumber(@Param("restaurantId") String restaurantId, @Param("operation") Integer operation);
}

