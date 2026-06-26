package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wang.zehui.self.cook.book.domain.entity.DishIngredient;

/**
 * 菜品食材表(DishIngredient)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-06-24 11:54:34
 */
@Mapper
public interface DishIngredientDao extends BaseMapper<DishIngredient> {
}

