package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import wang.zehui.self.cook.book.dao.RestaurantCommentDao;
import wang.zehui.self.cook.book.dao.UserLikeDao;
import wang.zehui.self.cook.book.domain.entity.UserLike;
import wang.zehui.self.cook.book.domain.request.LikeRequest;
import wang.zehui.self.cook.book.service.IUserLikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户点赞表(UserLike)表服务实现类
 *
 * @author wangzehui
 * @since 2026-07-21 14:58:19
 */
@Service
public class UserLikeServiceImpl extends ServiceImpl<UserLikeDao, UserLike> implements IUserLikeService {

    @Resource
    private RestaurantCommentDao restaurantCommentDao;

    @Override
    public Boolean addLike(LikeRequest request) {
        UserLike userLike = new UserLike();
        userLike.setUserId(request.getUserId());
        userLike.setLikeId(request.getLikeId());
        userLike.setLikeType(request.getLikeType());

        // 修改点赞数量
        restaurantCommentDao.addOrReduceLikeNumber(request.getLikeId(), 1);

        return this.save(userLike);
    }

    @Override
    public Boolean removeLike(LikeRequest request) {
        // 修改点赞数量
        restaurantCommentDao.addOrReduceLikeNumber(request.getLikeId(), -1);

        return this.remove(Wrappers.<UserLike>lambdaQuery()
                .eq(UserLike::getUserId, request.getUserId())
                .eq(UserLike::getLikeId, request.getLikeId())
                .eq(UserLike::getLikeType, request.getLikeType()));
    }

    @Override
    public List<String> getLikeIds(String userId, Integer likeType, Set<String> likeIds) {
        if (CollectionUtils.isEmpty(likeIds)) {
            return Collections.emptyList();
        }
        List<UserLike> userLikes = this.list(Wrappers.<UserLike>lambdaQuery()
                .eq(UserLike::getUserId, userId)
                .eq(UserLike::getLikeType, likeType)
                .in(UserLike::getLikeId, likeIds));
        return userLikes.stream().map(UserLike::getLikeId).collect(Collectors.toList());
    }
}

