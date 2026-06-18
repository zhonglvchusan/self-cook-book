package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wang.zehui.self.cook.book.domain.entity.RestaurantCategory;

/**
 * 餐厅分类表(RestaurantCategory)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-06-15 14:15:01
 */
@Mapper
public interface RestaurantCategoryDao extends BaseMapper<RestaurantCategory> {
}

