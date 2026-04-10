package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.domain.entity.Menu;
import wang.zehui.self.cook.book.domain.request.MenuRequest;
import wang.zehui.self.cook.book.domain.response.MenuInfoResponse;
import wang.zehui.self.cook.book.domain.response.MenuTreeResponse;

import java.util.List;

/**
 * 菜单表(Menu)表服务接口
 *
 * @author wangzehui
 * @since 2026-04-09 10:44:21
 */
public interface IMenuService extends IService<Menu> {

    /**
     * @Description: 新增/修改菜单
     * @param request 新增菜单参数
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/4/9 11:15
     */
    Boolean saveOrUpdateMenu(MenuRequest request);

    /**
     * @Description: 批量删除菜单
     * @param menuIds 菜单ID列表
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/4/9 11:20
     */
    Boolean batchDeleteMenu(List<String> menuIds);

    /**
     * @Description: 查询菜单树
     * @Return: java.util.List<wang.zehui.self.cook.book.domain.response.MenuTreeResponse>
     * @Author: wangzehui
     * @Date: 2026/4/9 12:10
     */
    List<MenuTreeResponse> getMenuTree();

    /**
     * @Description: 获取菜单详情
     * @param menuId 菜单ID
     * @Return: wang.zehui.self.cook.book.domain.response.MenuInfoResponse
     * @Author: wangzehui
     * @Date: 2026/4/10 10:28
     */
    MenuInfoResponse getMenuInfo(String menuId);
}

