package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.enums.MenuTypeEnum;
import wang.zehui.self.cook.book.common.utils.ConvertUtil;
import wang.zehui.self.cook.book.dao.MenuDao;
import wang.zehui.self.cook.book.domain.entity.Menu;
import wang.zehui.self.cook.book.domain.request.MenuRequest;
import wang.zehui.self.cook.book.domain.response.MenuInfoResponse;
import wang.zehui.self.cook.book.domain.response.MenuTreeResponse;
import wang.zehui.self.cook.book.service.IMenuService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单表(Menu)表服务实现类
 *
 * @author wangzehui
 * @since 2026-04-09 10:44:21
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements IMenuService {

    @Override
    public Boolean saveOrUpdateMenu(MenuRequest request) {
        Menu menu = new Menu();
        if (!Objects.isNull(request.getId())) {
            menu = this.getById(request.getId());
            if (Objects.isNull(menu)) {
                throw new BusinessException("菜单不存在");
            }
            if (menu.getDeletedFlag()) {
                throw new BusinessException("菜单已删除");
            }
        }
        if (!this.validateName(request)) {
            throw new BusinessException("菜单名称已存在");
        }
        if (!this.validateWebPerms(request)) {
            throw new BusinessException("前端权限字符串已存在");
        }
        if (!Objects.isNull(request.getId()) && request.getId().equals(request.getParentId())) {
            throw new BusinessException("父级菜单不能是自己");
        }

        BeanUtils.copyProperties(request, menu);

        return this.saveOrUpdate(menu);
    }

    @Override
    public Boolean batchDeleteMenu(List<String> menuIds) {
        if (CollectionUtils.isEmpty(menuIds)) {
            throw new BusinessException("请选择要删除的菜单");
        }

        // 递归删除菜单
        this.deleteByMenuIds(menuIds);
        return true;
    }

    @Override
    public List<MenuTreeResponse> getMenuTree() {
        List<Menu> menus = this.list(Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getDeletedFlag, false)
                .in(Menu::getMenuType, MenuTypeEnum.CATALOG.getCode(), MenuTypeEnum.MENU.getCode()));

        return this.buildMenuTree(menus);
    }

    @Override
    public MenuInfoResponse getMenuInfo(String menuId) {
        Menu menu = this.getById(menuId);

        if (Objects.isNull(menu)) {
            throw new BusinessException("菜单不存在");
        }

        if (menu.getDeletedFlag()) {
            throw new BusinessException("菜单已删除");
        }

        MenuInfoResponse menuInfoResponse = new MenuInfoResponse();
        BeanUtils.copyProperties(menu, menuInfoResponse);

        return menuInfoResponse;
    }

    /**
     * @Description: 校验菜单名称是否存在
     * @param menuRequest 菜单参数
     * @Return: java.lang.Boolean true: 名称可用 false: 名称已存在
     * @Author: wangzehui
     * @Date: 2026/4/9 12:30
     */
    private Boolean validateName(MenuRequest menuRequest) {
        Menu menu = this.getOne(Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getMenuName, menuRequest.getMenuName())
                .eq(Menu::getParentId, menuRequest.getParentId())
                .eq(Menu::getDeletedFlag, false)
                .last("LIMIT 1"));
        if (Objects.isNull(menuRequest.getId())) {
            return Objects.isNull(menu);
        }
        return Objects.isNull(menu) || menu.getId().equals(menuRequest.getId());
    }

    /**
     * @Description: 校验前端权限字符串是否存在
     * @param menuRequest 菜单参数
     * @Return: java.lang.Boolean true: 可用 false: 已存在
     * @Author: wangzehui
     * @Date: 2026/4/9 14:10
     */
    private Boolean validateWebPerms(MenuRequest menuRequest) {
        if (StringUtils.isEmpty(menuRequest.getWebPerms())) {
            return true;
        }

        Menu menu = this.getOne(Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getWebPerms, menuRequest.getWebPerms())
                .eq(Menu::getDeletedFlag, false)
                .last("LIMIT 1"));
        if (Objects.isNull(menuRequest.getId())) {
            return Objects.isNull(menu);
        }
        return Objects.isNull(menu) || menu.getId().equals(menuRequest.getId());
    }

    /**
     * @Description: 递归删除菜单
     * @param menuIds 菜单ID列表
     * @Return: void
     * @Author: wangzehui
     * @Date: 2026/4/9 15:32
     */
    private void deleteByMenuIds(List<String> menuIds) {
        this.update(Wrappers.<Menu>lambdaUpdate()
                .in(Menu::getId, menuIds)
                .set(Menu::getDeletedFlag, true));

        // 删除完毕后，查询子菜单
        List<Menu> childrenMenus = this.list(Wrappers.<Menu>lambdaQuery()
                .in(Menu::getParentId, menuIds)
                .select(Menu::getId));
        // 如果没有子菜单，递归结束
        if (CollectionUtils.isEmpty(childrenMenus)) {
            return;
        }
        // 存在子菜单，递归删除子菜单
        this.deleteByMenuIds(ConvertUtil.convertList(childrenMenus, Menu::getId));
    }

    /**
     * @Description: 构建菜单树
     * @param menus 菜单列表
     * @Return: java.util.List<wang.zehui.self.cook.book.domain.response.MenuTreeResponse>
     * @Author: wangzehui
     * @Date: 2026/4/9 16:32
     */
    private List<MenuTreeResponse> buildMenuTree(List<Menu> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }

        // 先转换对象
        List<MenuTreeResponse> menuTreeResponses = menus.stream()
                .map(menu -> {
                    MenuTreeResponse menuTreeResponse = new MenuTreeResponse();
                    BeanUtils.copyProperties(menu, menuTreeResponse);
                    return menuTreeResponse;
                }).collect(Collectors.toList());

        // 根据parentId进行分组
        Map<String, List<MenuTreeResponse>> parentMap = menuTreeResponses.stream()
                .collect(Collectors.groupingBy(MenuTreeResponse::getParentId));

        // 赋值子菜单
        menuTreeResponses.forEach(menuTreeResponse -> menuTreeResponse.setChildren(parentMap.get(menuTreeResponse.getId())));

        // 获取根节点
        return menuTreeResponses.stream().filter(menuTreeResponse -> menuTreeResponse.getParentId().equals("0")).collect(Collectors.toList());
    }
}

