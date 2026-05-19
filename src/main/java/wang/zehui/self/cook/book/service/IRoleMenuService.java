package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.domain.entity.Menu;
import wang.zehui.self.cook.book.domain.entity.RoleMenu;
import wang.zehui.self.cook.book.domain.request.RoleMenuUpdateRequest;
import wang.zehui.self.cook.book.domain.response.RoleMenuTreeResponse;

import java.util.List;

/**
 * 角色菜单表(RoleMenu)表服务接口
 *
 * @author wangzehui
 * @since 2026-04-10 14:46:09
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    /**
     * @Description: 更新角色菜单关系
     * @param request 更新参数
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/4/10 14:54
     */
    Boolean updateRoleMenu(RoleMenuUpdateRequest request);

    /**
     * @Description: 获取角色菜单列表
     * @param roleIds 角色ID列表
     * @param superAdministratorFlag 是否是超级管理员
     * @Return: java.util.List<wang.zehui.self.cook.book.domain.entity.Menu>
     * @Author: wangzehui
     * @Date: 2026/4/10 15:06
     */
    List<Menu> getMenuList(List<String> roleIds, Boolean superAdministratorFlag);

    /**
     * @Description: 获取角色已选菜单树
     * @param roleId 角色ID
     * @Return: wang.zehui.self.cook.book.domain.response.RoleMenuTreeResponse
     * @Author: wangzehui
     * @Date: 2026/4/10 15:18
     */
    RoleMenuTreeResponse getRoleSelectedMenu(String roleId);

    /**
     * @Description: 通过角色id删除角色菜单
     * @param roleId 角色id
     * @Return: void
     * @Author: wangzehui
     * @Date: 2026/4/17 14:28
     */
    void removeByRoleId(String roleId);
}

