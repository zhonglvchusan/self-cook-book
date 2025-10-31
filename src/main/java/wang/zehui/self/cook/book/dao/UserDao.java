package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wang.zehui.self.cook.book.domain.entity.User;

/**
 * (User)表数据库访问层
 *
 * @author wangzehui
 * @since 2025-10-29 16:59:43
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
}

