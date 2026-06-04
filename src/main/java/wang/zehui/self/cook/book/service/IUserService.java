package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.lang3.tuple.Pair;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.*;
import wang.zehui.self.cook.book.domain.response.UserInfoResponse;
import wang.zehui.self.cook.book.domain.response.UserListResponse;

/**
 * (User)表服务接口
 *
 * @author wangzehui
 * @since 2025-10-29 16:59:43
 */
public interface IUserService extends IService<User> {

    /**
     * @Description: 通过登录账号获取用户信息
     * @param loginName 登录账号
     * @Return: wang.zehui.self.cook.book.domain.entity.User
     * @Author: wangzehui
     * @Date: 2026/3/26 16:59
     */
    User getByLoginName(String loginName);

    /**
     * @Description: 通过openId或者unionId获取用户信息
     * @param userOpenIdAndUnionId 用户openId和unionId
     * @Return: wang.zehui.self.cook.book.domain.entity.User
     * @Author: wangzehui
     * @Date: 2026/6/4 10:13
     */
    User getByOpenIdOrUnionId(Pair<String, String> userOpenIdAndUnionId);

    /**
     * @Description: 注册用户
     * @param userAddRequest
     * @Return: java.lang.String
     * @Author: wangzehui
     * @Date: 2026/3/26 17:25
     */
    String registerUser(UserAddRequest userAddRequest);

    /**
     * @Description: 删除用户
     * @param userId 用户id
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/4/2 15:53
     */
    Boolean deleteUser(String userId);

    /**
     * @Description: 禁用/解禁用户
     *  若用户当前状态为禁用，则解禁；
     *  若用户当前状态为正常，则禁用
     * @param userId 用户id
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/4/2 15:54
     */
    Boolean changeUserState(String userId);

    /**
     * @Description: 修改用户信息
     * @param userUpdateRequest 修改用户信息表单
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/4/2 15:56
     */
    Boolean updateUser(UserUpdateRequest userUpdateRequest);

    /**
     * @Description: 获取用户列表
     * @param request 查询条件
     * @Return: wang.zehui.self.cook.book.common.domain<wang.zehui.self.cook.book.domain.response.UserListResponse>
     * @Author: wangzehui
     * @Date: 2026/4/2 15:57
     */
    PageResult<UserListResponse> getUserList(UserListRequest request);

    /**
     * @Description: 获取用户详情
     * @param userId 用户id
     * @Return: wang.zehui.self.cook.book.domain.response.UserInfoResponse
     * @Author: wangzehui
     * @Date: 2026/4/2 15:58
     */
    UserInfoResponse getUserInfo(String userId);

    /**
     * @Description: 修改密码
     * @param request 修改密码请求
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/5/28 16:22
     */
    Boolean changePassword(ChangePasswordRequest request);

    /**
     * @Description: 重置密码
     * @param userId 用户id
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/6/1 14:12
     */
    Boolean resetPassword(String userId);

    /**
     * @Description: 更新用户信息
     * @param request 修改用户信息表单
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/6/4 15:39
     */
    Boolean updateUser(ApiUserUpdateRequest request);
}

