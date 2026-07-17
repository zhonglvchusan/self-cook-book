package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wang.zehui.self.cook.book.domain.entity.RestaurantComment;

import java.util.List;

/**
 * 餐厅评论表(RestaurantComment)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-07-16 11:49:21
 */
@Mapper
public interface RestaurantCommentDao extends BaseMapper<RestaurantComment> {

    /**
     * @Description: 获取对根评论的回复列表
     * @param rootIds
     * @Return: java.util.List<wang.zehui.self.cook.book.domain.entity.RestaurantComment>
     * @Author: wangzehui
     * @Date: 2026/7/16 15:44
     */
    List<RestaurantComment> getTopRepliesByRootIds(@Param("rootIds") List<String> rootIds);
}

