package wang.zehui.self.cook.book.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import wang.zehui.self.cook.book.domain.request.MenuRequest;
import wang.zehui.self.cook.book.domain.response.MenuTreeResponse;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.service.IMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单表(Menu)表控制层
 *
 * @author wangzehui
 * @since 2026-04-09 10:44:21
 */
@Tag(name = "菜单相关接口")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @PostMapping
    @Operation(summary = "新增/修改菜单 @author wangzh")
    @SaCheckPermission("menu:saveOrUpdate")
    private ResponseDTO<Boolean> saveOrUpdateMenu(@RequestBody @Validated MenuRequest request) {
        return ResponseDTO.success(menuService.saveOrUpdateMenu(request));
    }

    @DeleteMapping
    @Operation(summary = "批量删除菜单 @author wangzh")
    @SaCheckPermission("menu:batchDelete")
    private ResponseDTO<Boolean> batchDeleteMenu(@RequestBody @Validated List<String> menuIds) {
        return ResponseDTO.success(menuService.batchDeleteMenu(menuIds));
    }

    @GetMapping
    @Operation(summary = "查询菜单树 @author wangzh")
    private ResponseDTO<List<MenuTreeResponse>> getMenuTree() {
        return ResponseDTO.success(menuService.getMenuTree());
    }
}

