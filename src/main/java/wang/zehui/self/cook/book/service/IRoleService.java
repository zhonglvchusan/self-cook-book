package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.entity.Role;
import wang.zehui.self.cook.book.domain.request.RoleRequest;
import wang.zehui.self.cook.book.domain.request.RoleSearchRequest;
import wang.zehui.self.cook.book.domain.response.RoleInfoResponse;
import wang.zehui.self.cook.book.domain.response.RoleListResponse;

import java.util.List;

/**
 * 角色菜单表(Role)表服务接口
 *
 * @author wangzehui
 * @since 2026-04-10 10:03:49
 */
public interface IRoleService extends IService<Role> {

    /**
     * @Description: 新增/修改角色信息
     * @param request 新增/修改角色参数
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/4/10 10:39
     */
    Boolean saveOrUpdateRole(RoleRequest request);

    /**
     * @Description: 删除角色
     * @param roleId 角色ID
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/4/10 10:40
     */
    Boolean deleteRole(String roleId);

    /**
     * @Description: 查询角色分页列表
     * @param request 查询参数
     * @Return: wang.zehui.self.cook.book.common.domain.PageResult<wang.zehui.self.cook.book.domain.response.RoleListResponse>
     * @Author: wangzehui
     * @Date: 2026/4/10 10:42
     */
    PageResult<RoleListResponse> getRoleList(RoleSearchRequest request);

    /**
     * @Description: 获取角色详情
     * @param roleId 角色ID
     * @Return: wang.zehui.self.cook.book.domain.response.RoleInfoResponse
     * @Author: wangzehui
     * @Date: 2026/4/10 10:43
     */
    RoleInfoResponse getRoleInfo(String roleId);

}