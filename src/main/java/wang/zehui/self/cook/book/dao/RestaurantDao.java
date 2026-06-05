package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wang.zehui.self.cook.book.domain.entity.Restaurant;

/**
 * 餐厅信息表(Restaurant)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-06-03 14:58:48
 */
@Mapper
public interface RestaurantDao extends BaseMapper<Restaurant> {
}

