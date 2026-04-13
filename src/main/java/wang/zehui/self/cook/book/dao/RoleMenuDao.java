package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wang.zehui.self.cook.book.domain.entity.RoleMenu;

/**
 * 角色菜单表(RoleMenu)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-04-10 14:46:09
 */
@Mapper
public interface RoleMenuDao extends BaseMapper<RoleMenu> {
}

