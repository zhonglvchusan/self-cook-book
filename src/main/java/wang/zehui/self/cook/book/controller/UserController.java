package wang.zehui.self.cook.book.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.request.ChangePasswordRequest;
import wang.zehui.self.cook.book.domain.request.UserAddRequest;
import wang.zehui.self.cook.book.domain.request.UserListRequest;
import wang.zehui.self.cook.book.domain.request.UserUpdateRequest;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.domain.response.UserInfoResponse;
import wang.zehui.self.cook.book.domain.response.UserListResponse;
import wang.zehui.self.cook.book.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (User)表控制层
 *
 * @author wangzehui
 * @since 2025-11-06 11:19:18
 */
@Tag(name = "用户相关接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping
    @Operation(summary = "添加用户 @author wangzh")
    @SaCheckPermission("user:add")
    public ResponseDTO<String> registerUser(@RequestBody @Validated UserAddRequest userAddRequest) {
        return ResponseDTO.success(ResponseDTO.SUCCESS_CODE, userService.registerUser(userAddRequest));
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "删除用户 @author wangzh")
    @SaCheckPermission("user:delete")
    public ResponseDTO<Boolean> deleteUser(@PathVariable String userId) {
        return ResponseDTO.success(userService.deleteUser(userId));
    }

    @PutMapping("/{userId}")
    @Operation(summary = "禁用/解禁用户 @author wangzh")
    @SaCheckPermission("user:change")
    public ResponseDTO<Boolean> changeUserState(@PathVariable String userId) {
        return ResponseDTO.success(userService.changeUserState(userId));
    }

    @PutMapping
    @Operation(summary = "修改用户信息 @author wangzh")
    @SaCheckPermission("user:update")
    public ResponseDTO<Boolean> updateUser(@RequestBody @Validated UserUpdateRequest userUpdateRequest) {
        return ResponseDTO.success(userService.updateUser(userUpdateRequest));
    }

    @GetMapping
    @Operation(summary = "获取用户列表 @author wangzh")
    @SaCheckPermission("user:list")
    public ResponseDTO<PageResult<UserListResponse>> getUserList(@Validated UserListRequest request) {
        return ResponseDTO.success(userService.getUserList(request));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "获取用户详情 @author wangzh")
    public ResponseDTO<UserInfoResponse> getUserInfo(@PathVariable String userId) {
        return ResponseDTO.success(userService.getUserInfo(userId));
    }

    @PostMapping("/change/password")
    @Operation(summary = "修改密码 @author wangzh")
    public ResponseDTO<Boolean> changePassword(@RequestBody @Validated ChangePasswordRequest request) {
        return ResponseDTO.success(userService.changePassword(request));
    }

    @PutMapping("/reset/password/{userId}")
    @Operation(summary = "重置密码 @author wangzh")
    @SaCheckPermission("user:reset")
    public ResponseDTO<Boolean> resetPassword(@PathVariable String userId) {
        return ResponseDTO.success(userService.resetPassword(userId));
    }

}

