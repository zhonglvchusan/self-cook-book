package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wang.zehui.self.cook.book.domain.entity.UserLike;

/**
 * 用户点赞表(UserLike)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-07-21 14:58:19
 */
@Mapper
public interface UserLikeDao extends BaseMapper<UserLike> {
}

