package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.utils.ConvertUtil;
import wang.zehui.self.cook.book.dao.RoleDao;
import wang.zehui.self.cook.book.dao.RoleMenuDao;
import wang.zehui.self.cook.book.domain.entity.Menu;
import wang.zehui.self.cook.book.domain.entity.Role;
import wang.zehui.self.cook.book.domain.entity.RoleMenu;
import wang.zehui.self.cook.book.domain.request.RoleMenuUpdateRequest;
import wang.zehui.self.cook.book.domain.response.MenuSimpleTreeResponse;
import wang.zehui.self.cook.book.domain.response.RoleMenuTreeResponse;
import wang.zehui.self.cook.book.service.IMenuService;
import wang.zehui.self.cook.book.service.IRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色菜单表(RoleMenu)表服务实现类
 *
 * @author wangzehui
 * @since 2026-04-10 14:46:09
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenu> implements IRoleMenuService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IMenuService menuService;

    @Override
    @Transactional(rollbackFor = { Exception.class, Error.class, BusinessException.class})
    public Boolean updateRoleMenu(RoleMenuUpdateRequest request) {
        Role role = roleDao.selectById(request.getRoleId());
        if (Objects.isNull(role)) {
            throw new BusinessException("角色不存在");
        }

        List<RoleMenu> roleMenus = new ArrayList<>();
        request.getMenuIds().forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(request.getRoleId());
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        });

        // 先删除旧的，再添加新的
        this.remove(Wrappers.<RoleMenu>lambdaQuery()
                .eq(RoleMenu::getRoleId, request.getRoleId()));

        return this.saveBatch(roleMenus);
    }

    @Override
    public List<Menu> getMenuList(List<String> roleIds, Boolean superAdministratorFlag) {
        // 管理员，返回全部菜单
        if (superAdministratorFlag) {
            return menuService.list(Wrappers.<Menu>lambdaQuery()
                    .eq(Menu::getDeletedFlag, false));
        }
        // 非管理员 无角色 返回空菜单
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }

        List<String> menuIds = this.getRoleMenuIds(roleIds);

        return menuService.list(Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getDeletedFlag, false)
                .in(Menu::getId, menuIds));
    }

    @Override
    public RoleMenuTreeResponse getRoleSelectedMenu(String roleId) {
        RoleMenuTreeResponse response = new RoleMenuTreeResponse();
        response.setRoleId(roleId);

        // 角色所选择的菜单权限
        List<String> selectedMenuIds = this.getRoleMenuIds(Collections.singletonList(roleId));
        response.setSelectedMenuIds(selectedMenuIds);

        // 菜单描述
        List<Menu> menus = menuService.list(Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getDeletedFlag, false)
                .eq(Menu::getDisabledFlag, false));
        List<MenuSimpleTreeResponse> menuSimpleTreeResponses = this.buildMenuSimpleTree(menus);
        response.setMenuTree(menuSimpleTreeResponses);

        return response;
    }

    @Override
    public void removeByRoleId(String roleId) {
        this.remove(Wrappers.<RoleMenu>lambdaQuery()
                .eq(RoleMenu::getRoleId, roleId));
    }

    /**
     * @Description: 获取角色id所拥有的菜单id
     * @param roleIds 角色id列表
     * @Return: java.util.List<java.lang.String>
     * @Author: wangzehui
     * @Date: 2026/4/10 15:22
     */
    private List<String> getRoleMenuIds(List<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        List<RoleMenu> menus = this.list(Wrappers.<RoleMenu>lambdaQuery()
                .select(RoleMenu::getMenuId)
                .in(RoleMenu::getRoleId, roleIds));
        return ConvertUtil.convertList(menus, RoleMenu::getMenuId);
    }

    /**
     * @Description: 构建菜单树，仅包含名称、id等字段
     * @param menus 菜单列表
     * @Return: java.util.List<wang.zehui.self.cook.book.domain.response.MenuSimpleTreeResponse>
     * @Author: wangzehui
     * @Date: 2026/4/10 15:35
     */
    private List<MenuSimpleTreeResponse> buildMenuSimpleTree(List<Menu> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }

        List<MenuSimpleTreeResponse> menuSimpleTreeResponses = menus.stream()
                .map(menu -> {
                    MenuSimpleTreeResponse response = new MenuSimpleTreeResponse();
                    BeanUtils.copyProperties(menu, response);
                    return response;
                })
                .collect(Collectors.toList());

        Map<String, List<MenuSimpleTreeResponse>> parentMap = menuSimpleTreeResponses.stream().collect(Collectors.groupingBy(MenuSimpleTreeResponse::getParentId));

        menuSimpleTreeResponses.forEach(menu -> {
            List<MenuSimpleTreeResponse> childrenMenus = parentMap.get(menu.getId());
            menu.setChildren(childrenMenus);
        });

        return menuSimpleTreeResponses.stream()
                .filter(menu -> menu.getParentId().equals("0"))
                .collect(Collectors.toList());
    }
}

