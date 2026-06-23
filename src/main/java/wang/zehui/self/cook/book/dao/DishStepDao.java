package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wang.zehui.self.cook.book.domain.entity.DishStep;

/**
 * 菜品步骤表(DishStep)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-06-23 14:18:09
 */
@Mapper
public interface DishStepDao extends BaseMapper<DishStep> {
}

