package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wang.zehui.self.cook.book.domain.entity.Ingredient;

/**
 * 食材表(Ingredient)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-06-24 10:51:24
 */
@Mapper
public interface IngredientDao extends BaseMapper<Ingredient> {
}

