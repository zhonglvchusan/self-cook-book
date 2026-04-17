package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.entity.RoleUser;
import wang.zehui.self.cook.book.domain.request.RoleUserUpdateRequest;
import wang.zehui.self.cook.book.domain.request.RoleUserRequest;
import wang.zehui.self.cook.book.domain.response.RoleInfoResponse;
import wang.zehui.self.cook.book.domain.response.UserListResponse;

import java.util.List;

/**
 * 角色用户关系表(RoleUser)表服务接口
 *
 * @author wangzehui
 * @since 2026-04-13 11:45:33
 */
public interface IRoleUserService extends IService<RoleUser> {

    /**
     * @Description: 分页查询角色下的用户
     * @param request 查询参数
     * @Return: wang.zehui.self.cook.book.common.domain.PageResult<wang.zehui.self.cook.book.domain.response.UserListResponse>
     * @Author: wangzehui
     * @Date: 2026/4/15 10:14
     */
    PageResult<UserListResponse> getUserPageList(RoleUserRequest request);

    /**
     * @Description: 批量移除用户角色
     * @param request 批量移除参数
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/4/17 10:26
     */
    Boolean deleteRoleUserBatch(RoleUserUpdateRequest request);

    /**
     * @Description: 批量给用户增加角色
     * @param request 批量增加参数
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/4/17 10:29
     */
    Boolean roleUserAddBatch(RoleUserUpdateRequest request);

    /**
     * @Description: 获取用户角色列表
     * @param userId 用户id
     * @Return: java.util.List<wang.zehui.self.cook.book.domain.response.RoleResponse>
     * @Author: wangzehui
     * @Date: 2026/4/17 10:31
     */
    List<RoleInfoResponse> getRoleByUserId(String userId);

    /**
     * @Description: 检查角色下是否存在用户
     * @param roleId 角色id
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/4/17 14:21
     */
    Boolean existsByRoleId(String roleId);

    /**
     * @Description: 通过角色id删除角色用户关系
     * @param roleId 角色id
     * @Return: void
     * @Author: wangzehui
     * @Date: 2026/4/17 14:26
     */
    void removeByRoleId(String roleId);
}

