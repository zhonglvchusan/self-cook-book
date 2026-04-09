package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wang.zehui.self.cook.book.domain.entity.Menu;

/**
 * 菜单表(Menu)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-04-09 10:44:21
 */
@Mapper
public interface MenuDao extends BaseMapper<Menu> {
}

