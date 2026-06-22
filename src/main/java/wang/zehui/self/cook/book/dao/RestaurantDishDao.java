package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wang.zehui.self.cook.book.domain.entity.RestaurantDish;

/**
 * 菜品分类菜品表(RestaurantDish)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-06-22 14:24:42
 */
@Mapper
public interface RestaurantDishDao extends BaseMapper<RestaurantDish> {
}

