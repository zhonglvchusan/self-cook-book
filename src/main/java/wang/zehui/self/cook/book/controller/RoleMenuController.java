package wang.zehui.self.cook.book.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import wang.zehui.self.cook.book.domain.request.RoleMenuUpdateRequest;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.domain.response.RoleMenuTreeResponse;
import wang.zehui.self.cook.book.service.IRoleMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色菜单表(RoleMenu)表控制层
 *
 * @author wangzehui
 * @since 2026-04-10 14:46:09
 */
@Tag(name = "角色菜单相关接口")
@RestController
@RequestMapping("/role/menu")
public class RoleMenuController {

    @Resource
    private IRoleMenuService roleMenuService;

    @PostMapping
    @Operation(summary = "更新角色菜单 @author wangzh")
    @SaCheckPermission("role:menu:update")
    public ResponseDTO<Boolean> updateRoleMenu(@RequestBody RoleMenuUpdateRequest request) {
        return ResponseDTO.success(roleMenuService.updateRoleMenu(request));
    }

    @GetMapping("/{roleId}")
    @Operation(summary = "获取角色所选菜单 @author wangzh")
    @SaCheckPermission("role:menu:get")
    public ResponseDTO<RoleMenuTreeResponse> getRoleSelectedMenu(@PathVariable String roleId) {
        return ResponseDTO.success(roleMenuService.getRoleSelectedMenu(roleId));
    }
}

