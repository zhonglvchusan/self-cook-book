package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.common.enums.CommentTypeEnum;
import wang.zehui.self.cook.book.common.enums.ErrorCodeEnum;
import wang.zehui.self.cook.book.common.utils.ConvertUtil;
import wang.zehui.self.cook.book.common.utils.RequestUtil;
import wang.zehui.self.cook.book.dao.RestaurantCommentDao;
import wang.zehui.self.cook.book.domain.entity.Restaurant;
import wang.zehui.self.cook.book.domain.entity.RestaurantComment;
import wang.zehui.self.cook.book.domain.entity.RestaurantDish;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.AddCommentRequest;
import wang.zehui.self.cook.book.domain.request.AdminCommentSearchRequest;
import wang.zehui.self.cook.book.domain.request.CommentSearchRequest;
import wang.zehui.self.cook.book.domain.request.MoreCommentRequest;
import wang.zehui.self.cook.book.domain.response.AdminCommentListResponse;
import wang.zehui.self.cook.book.domain.response.CommentListResponse;
import wang.zehui.self.cook.book.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 餐厅评论表(RestaurantComment)表服务实现类
 *
 * @author wangzehui
 * @since 2026-07-16 11:49:22
 */
@Service
public class RestaurantCommentServiceImpl extends ServiceImpl<RestaurantCommentDao, RestaurantComment> implements IRestaurantCommentService {

    @Resource
    private IRestaurantService restaurantService;

    @Resource
    private RestaurantCommentDao restaurantCommentDao;

    @Resource
    private IRestaurantDishService restaurantDishService;

    @Resource
    private IUserService userService;

    @Resource
    private IUserLikeService userLikeService;

    @Override
    public Boolean addComment(AddCommentRequest request) {
        restaurantService.checkRestaurant(request.getRestaurantId());

        RestaurantComment comment = new RestaurantComment();
        BeanUtils.copyProperties(request, comment);
        comment.setUserId(RequestUtil.getUserId());
        // 回复的id没有值，则是评论
        comment.setType(StringUtils.isBlank(request.getParentId()) ? CommentTypeEnum.COMMENT.getValue() : CommentTypeEnum.REPLY.getValue());

        return this.save(comment);
    }

    @Override
    public Boolean deleteComment(String commentId) {
        return this.removeById(commentId);
    }

    @Override
    public PageResult<CommentListResponse> getRestaurantComments(CommentSearchRequest request) {
        if (StringUtils.isBlank(request.getRestaurantId())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
        }

        // 先获取10条根评论
        LambdaQueryWrapper<RestaurantComment> queryWrapper = Wrappers.<RestaurantComment>lambdaQuery()
                .eq(RestaurantComment::getRestaurantId, request.getRestaurantId())
                .isNull(RestaurantComment::getRootId)
                .eq(!StringUtils.isBlank(request.getDishId()), RestaurantComment::getDishId, request.getDishId());

        Page<RestaurantComment> page = new Page<>(request.getPageNum(), request.getPageSize());
        this.page(page, queryWrapper);

        List<RestaurantComment> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResult.of(page);
        }

        // 根据 根评论，每条根评论查询三条评论
        List<String> rootIds = ConvertUtil.convertList(records, RestaurantComment::getId);
        List<RestaurantComment> topRepliesByRootIds = restaurantCommentDao.getTopRepliesByRootIds(rootIds);

        // 处理用户是否点赞
        HashSet<String> queryLikeCommentIds = new HashSet<>(rootIds);
        queryLikeCommentIds.addAll(ConvertUtil.convertList(topRepliesByRootIds, RestaurantComment::getId));
        List<String> likeIds = userLikeService.getLikeIds(RequestUtil.getUserId(), CommentTypeEnum.COMMENT.getValue(), queryLikeCommentIds);

        // 菜品id
        List<String> dishIds = ConvertUtil.convertList(records, RestaurantComment::getDishId);
        Map<String, RestaurantDish> dishMap = restaurantDishService.getDishMapByIds(dishIds);

        // 用户id
        List<String> userIds = ConvertUtil.convertList(records, RestaurantComment::getUserId);
        // 子回复的userId也需要查询
        HashSet<String> userIdSet = new HashSet<>(userIds);
        userIdSet.addAll(ConvertUtil.convertList(topRepliesByRootIds, RestaurantComment::getUserId));
        Map<String, User> userMap = userService.getUserMap(new ArrayList<>(userIdSet));
        // 存在回复，才构建
        Map<String, List<CommentListResponse>> childrenCommentResponseMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(topRepliesByRootIds)) {
            List<String> parentIds = ConvertUtil.convertList(topRepliesByRootIds, RestaurantComment::getParentId);
            Map<String, RestaurantComment> parentCommentMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(parentIds)) {
                List<RestaurantComment> parentComments = this.listByIds(parentIds);
                parentCommentMap = ConvertUtil.convertMap(parentComments, RestaurantComment::getId, Function.identity());
                List<String> replyUserIds = ConvertUtil.convertList(parentComments, RestaurantComment::getUserId);
                Set<String> replyUserIdSet = replyUserIds.stream().filter(userId -> !userMap.containsKey(userId)).collect(Collectors.toSet());
                if (!CollectionUtils.isEmpty(replyUserIdSet)) {
                    Map<String, User> replyUserMap = userService.getUserMap(new ArrayList<>(replyUserIdSet));
                    userMap.putAll(replyUserMap);
                }
            }

            // 通过根评论分组
            Map<String, List<RestaurantComment>> childrenComments = topRepliesByRootIds.stream()
                    .collect(Collectors.groupingBy(RestaurantComment::getRootId));
            // 将评论回复构建为统一对象
            Map<String, RestaurantComment> finalParentCommentMap = parentCommentMap;
            childrenComments.forEach((rootId, children) -> {
                List<CommentListResponse> childrenResponses = children.stream().map(childrenComment -> {
                    CommentListResponse childrenCommentResponse = new CommentListResponse();
                    BeanUtils.copyProperties(childrenComment, childrenCommentResponse);
                    User user = userMap.get(childrenComment.getUserId());
                    childrenCommentResponse.setUserName(Objects.isNull(user) ? "" : user.getNickname());
                    if (!StringUtils.isBlank(childrenComment.getParentId())) {
                        RestaurantComment parentComment = finalParentCommentMap.get(childrenComment.getParentId());
                        childrenCommentResponse.setReplyUserId(Objects.isNull(parentComment) ? "" : parentComment.getUserId());
                        childrenCommentResponse.setReplyUserName(Objects.isNull(parentComment) ? "" : userMap.get(parentComment.getUserId()).getNickname());
                    }
                    childrenCommentResponse.setLikeStatus(likeIds.contains(childrenComment.getId()));
                    return childrenCommentResponse;
                }).collect(Collectors.toList());
                childrenCommentResponseMap.put(rootId, childrenResponses);
            });
        }

        return PageResult.of(page, comment -> {
            CommentListResponse response = new CommentListResponse();
            BeanUtils.copyProperties(comment, response);
            RestaurantDish restaurantDish = dishMap.get(comment.getDishId());
            response.setDishName(Objects.isNull(restaurantDish) ? "" : restaurantDish.getDishName());
            response.setUserName(Objects.isNull(userMap.get(comment.getUserId())) ? "" : userMap.get(comment.getUserId()).getNickname());
            response.setChildrenReplies(childrenCommentResponseMap.get(comment.getId()));
            if (!CollectionUtils.isEmpty(childrenCommentResponseMap.get(comment.getId()))) {
                response.setRemainingCount(childrenCommentResponseMap.get(comment.getId()).get(0).getRemainingCount());
            }
            response.setLikeStatus(likeIds.contains(comment.getId()));
            return response;
        });
    }

    @Override
    public PageResult<CommentListResponse> getMoreChildComment(MoreCommentRequest request) {
        Page<RestaurantComment> page = new Page<>(request.getPageNum(), request.getPageSize());
        this.page(page, Wrappers.<RestaurantComment>lambdaQuery()
                .eq(RestaurantComment::getRootId, request.getCommentId()));

        if (CollectionUtils.isEmpty(page.getRecords())) {
            return PageResult.of(page);
        }

        // 查询回复的用户id与名称
        List<String> parentIds = ConvertUtil.convertList(page.getRecords(), RestaurantComment::getParentId);
        List<RestaurantComment> restaurantParentComments = new ArrayList<>();
        Map<String, RestaurantComment> parentCommentMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(parentIds)) {
            restaurantParentComments = this.listByIds(parentIds);
            parentCommentMap = ConvertUtil.convertMap(restaurantParentComments, RestaurantComment::getId, Function.identity());
        }

        List<String> userIds = ConvertUtil.convertList(page.getRecords(), RestaurantComment::getUserId);
        HashSet<String> userIdSet = new HashSet<>(userIds);
        userIdSet.addAll(ConvertUtil.convertList(restaurantParentComments, RestaurantComment::getUserId));
        Map<String, User> userMap = userService.getUserMap(new ArrayList<>(userIdSet));

        Map<String, RestaurantComment> finalParentCommentMap = parentCommentMap;
        return PageResult.of(page, comment -> {
            CommentListResponse childrenCommentResponse = new CommentListResponse();
            BeanUtils.copyProperties(comment, childrenCommentResponse);
            User user = userMap.get(comment.getUserId());
            childrenCommentResponse.setUserName(Objects.isNull(user) ? "" : user.getNickname());
            if (!StringUtils.isBlank(comment.getParentId())) {
                RestaurantComment parentComment = finalParentCommentMap.get(comment.getParentId());
                childrenCommentResponse.setReplyUserId(Objects.isNull(parentComment) ? "" : parentComment.getUserId());
                childrenCommentResponse.setReplyUserName(Objects.isNull(parentComment) ? "" : userMap.get(parentComment.getUserId()).getNickname());
            }
            return childrenCommentResponse;
        });
    }

    @Override
    public PageResult<AdminCommentListResponse> getAdminCommentList(AdminCommentSearchRequest request) {
        LambdaQueryWrapper<RestaurantComment> queryWrapper = Wrappers.<RestaurantComment>lambdaQuery()
                .eq(!StringUtils.isBlank(request.getRestaurantId()), RestaurantComment::getRestaurantId, request.getRestaurantId())
                .ge(!StringUtils.isBlank(request.getStartTime()), RestaurantComment::getCreateTime, request.getStartTime())
                .le(!StringUtils.isBlank(request.getEndTime()), RestaurantComment::getCreateTime, request.getEndTime());

        Page<RestaurantComment> page = new Page<>(request.getPageNum(), request.getPageSize());
        this.page(page, queryWrapper);

        List<RestaurantComment> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResult.of(page);
        }

        List<String> restaurantIds = ConvertUtil.convertList(records, RestaurantComment::getRestaurantId);
        Map<String, Restaurant> restaurantMap = restaurantService.getRestaurantMap(new HashSet<>(restaurantIds));
        List<String> dishIds = ConvertUtil.convertList(records, RestaurantComment::getDishId);
        Map<String, RestaurantDish> dishMap = restaurantDishService.getDishMapByIds(dishIds);
        List<String> userIds = ConvertUtil.convertList(records, RestaurantComment::getUserId);
        Map<String, User> userMap = userService.getUserMap(userIds);

        return PageResult.of(page, comment -> {
            AdminCommentListResponse response = new AdminCommentListResponse();
            BeanUtils.copyProperties(comment, response);
            response.setRestaurantName(Objects.isNull(restaurantMap.get(comment.getRestaurantId())) ? "" : restaurantMap.get(comment.getRestaurantId()).getRestaurantName());
            response.setDishName(Objects.isNull(dishMap.get(comment.getDishId())) ? "" : dishMap.get(comment.getDishId()).getDishName());
            response.setUserName(Objects.isNull(userMap.get(comment.getUserId())) ? "" : userMap.get(comment.getUserId()).getNickname());

            return response;
        });
    }

}

