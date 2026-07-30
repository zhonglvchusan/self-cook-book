package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.domain.entity.UserLike;
import wang.zehui.self.cook.book.domain.request.LikeRequest;

import java.util.List;
import java.util.Set;

/**
 * 用户点赞表(UserLike)表服务接口
 *
 * @author wangzehui
 * @since 2026-07-21 14:58:19
 */
public interface IUserLikeService extends IService<UserLike> {

    /**
     * @Description: 添加点赞
     * @param request 点赞请求
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/7/30 10:00
     */
    Boolean addLike(LikeRequest request);

    /**
     * @Description: 删除点赞
     * @param request 点赞请求
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/7/30 10:00
     */
    Boolean removeLike(LikeRequest request);

    /**
     * @Description: 获取用户指定列表指定类型的点赞id
     * @param userId 用户id
     * @param likeType 点赞类型
     * @param likeIds 查询的id集合
     * @Return: java.util.List<java.lang.String>
     * @Author: wangzehui
     * @Date: 2026/7/30 10:01
     */
    List<String> getLikeIds(String userId, Integer likeType, Set<String> likeIds);
}