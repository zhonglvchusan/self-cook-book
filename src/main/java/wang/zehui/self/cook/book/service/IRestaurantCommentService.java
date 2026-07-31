package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.entity.RestaurantComment;
import wang.zehui.self.cook.book.domain.request.AddCommentRequest;
import wang.zehui.self.cook.book.domain.request.AdminCommentSearchRequest;
import wang.zehui.self.cook.book.domain.request.CommentSearchRequest;
import wang.zehui.self.cook.book.domain.request.MoreCommentRequest;
import wang.zehui.self.cook.book.domain.response.AdminCommentListResponse;
import wang.zehui.self.cook.book.domain.response.CommentListResponse;

/**
 * 餐厅评论表(RestaurantComment)表服务接口
 *
 * @author wangzehui
 * @since 2026-07-16 11:49:22
 */
public interface IRestaurantCommentService extends IService<RestaurantComment> {

    /**
     * @Description: 评论
     * @param request 评论信息
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/7/16 11:55
     */
    Boolean addComment(AddCommentRequest request);

    /**
     * @Description: 删除评论
     * @param commentId 评论id
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/7/16 14:18
     */
    Boolean deleteComment(String commentId);

    /**
     * @Description: 获取餐厅评价列表 支持只看某个餐品
     * @param request 搜索参数
     * @Return: wang.zehui.self.cook.book.common.domain.PageResult<wang.zehui.self.cook.book.domain.response.CommentListResponse>
     * @Author: wangzehui
     * @Date: 2026/7/16 14:34
     */
    PageResult<CommentListResponse> getRestaurantComments(CommentSearchRequest request);

    /**
     * @Description: 查看更多回复
     * @param request 更多回复请求
     * @Return: wang.zehui.self.cook.book.common.domain.PageResult<wang.zehui.self.cook.book.domain.response.CommentListResponse>
     * @Author: wangzehui
     * @Date: 2026/7/16 14:24
     */
    PageResult<CommentListResponse> getMoreChildComment(MoreCommentRequest request);

    /******************************************** 以下为后台方法 **************************************/

    /**
     * @Description: 获取评论列表
     * @param request
     * @Return: wang.zehui.self.cook.book.common.domain.PageResult<wang.zehui.self.cook.book.domain.response.AdminCommentListResponse>
     * @Author: wangzehui
     * @Date: 2026/7/30 14:44
     */
    PageResult<AdminCommentListResponse> getAdminCommentList(AdminCommentSearchRequest request);
}

