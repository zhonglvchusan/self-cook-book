package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wang.zehui.self.cook.book.domain.entity.Role;

/**
 * 角色菜单表(Role)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-04-10 10:03:48
 */
@Mapper
public interface RoleDao extends BaseMapper<Role> {
}

