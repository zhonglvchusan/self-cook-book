package wang.zehui.self.cook.book.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.request.RoleRequest;
import wang.zehui.self.cook.book.domain.request.RoleSearchRequest;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.domain.response.RoleInfoResponse;
import wang.zehui.self.cook.book.domain.response.RoleListResponse;
import wang.zehui.self.cook.book.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色菜单表(Role)表控制层
 *
 * @author wangzehui
 * @since 2026-04-10 10:03:48
 */
@Tag(name = "角色相关接口")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @PostMapping
    @Operation(summary = "新增/修改角色信息 @author wangzh")
    @SaCheckPermission("role:saveOrUpdate")
    public ResponseDTO<Boolean> saveOrUpdateRole(@RequestBody @Validated RoleRequest request) {
        return roleService.saveOrUpdateRole(request) ? ResponseDTO.success() : ResponseDTO.error();
    }

    @DeleteMapping("/{roleId}")
    @Operation(summary = "删除角色 @author wangzh")
    @SaCheckPermission("role:delete")
    public ResponseDTO<Boolean> deleteRole(@PathVariable String roleId) {
        return roleService.deleteRole(roleId) ? ResponseDTO.success() : ResponseDTO.error();
    }

    @GetMapping
    @Operation(summary = "获取角色列表 @author wangzh")
    @SaCheckPermission("role:list")
    public ResponseDTO<PageResult<RoleListResponse>> getRoleList(RoleSearchRequest request) {
        return ResponseDTO.success(roleService.getRoleList(request));
    }

    @GetMapping("/{roleId}")
    @Operation(summary = "获取角色信息 @author wangzh")
    @SaCheckPermission("role:info")
    public ResponseDTO<RoleInfoResponse> getRoleInfo(@PathVariable String roleId) {
        return ResponseDTO.success(roleService.getRoleInfo(roleId));
    }

}

