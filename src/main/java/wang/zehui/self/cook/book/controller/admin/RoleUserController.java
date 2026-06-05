package wang.zehui.self.cook.book.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.request.RoleUserRequest;
import wang.zehui.self.cook.book.domain.request.RoleUserUpdateRequest;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.domain.response.UserListResponse;
import wang.zehui.self.cook.book.service.IRoleUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色用户关系表(RoleUser)表控制层
 *
 * @author wangzehui
 * @since 2026-04-13 11:45:33
 */
@Tag(name = "角色用户关系相关")
@RestController
@RequestMapping("/role/user")
public class RoleUserController {

    @Resource
    private IRoleUserService roleUserService;

    @GetMapping
    @Operation(summary = "分页查询角色下用户信息 @author wangzh")
    @SaCheckPermission("role:user:list")
    public ResponseDTO<PageResult<UserListResponse>> getUserPageList(RoleUserRequest request) {
        return ResponseDTO.success(roleUserService.getUserPageList(request));
    }

    @DeleteMapping
    @Operation(summary = "批量移除角色下用户 @author wangzh")
    @SaCheckPermission("role:user:delete")
    public ResponseDTO<Boolean> deleteRoleUserBatch(@RequestBody @Validated RoleUserUpdateRequest request) {
        return ResponseDTO.success(roleUserService.deleteRoleUserBatch(request));
    }

    @PostMapping
    @Operation(summary = "批量增加角色下用户 @author wangzh")
    @SaCheckPermission("role:user:add")
    public ResponseDTO<Boolean> roleUserAddBatch(@RequestBody @Validated RoleUserUpdateRequest request) {
        return ResponseDTO.success(roleUserService.roleUserAddBatch(request));
    }

}

