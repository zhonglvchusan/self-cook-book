package wang.zehui.self.cook.book.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wang.zehui.self.cook.book.common.annoation.NoNeedLogin;
import wang.zehui.self.cook.book.domain.request.ApiUserUpdateRequest;
import wang.zehui.self.cook.book.domain.request.EmailSendRequest;
import wang.zehui.self.cook.book.domain.request.ForgetPasswordRequest;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.domain.response.UserInfoResponse;
import wang.zehui.self.cook.book.service.IUserService;

/**
 * @Author wangzehui
 * @Date 2026/6/4 15:00
 */
@Tag(name = "Api用户相关接口")
@RestController
@RequestMapping("/user")
public class ApiUserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{userId}")
    @Operation(summary = "获取用户详情 @author wangzh")
    public ResponseDTO<UserInfoResponse> getUserInfo(@PathVariable String userId) {
        return ResponseDTO.success(userService.getUserInfo(userId));
    }

    @PutMapping
    @Operation(summary = "修改个人信息 @author wangzh")
    public ResponseDTO<Boolean> updateUser(@RequestBody @Validated ApiUserUpdateRequest request) {
        return ResponseDTO.success(userService.updateUser(request));
    }

    @PostMapping("/change/password")
    @Operation(summary = "修改密码 @author wangzh")
    public ResponseDTO<Boolean> changePassword(@RequestBody @Validated ForgetPasswordRequest request) {
        return ResponseDTO.success(userService.forgetPassword(request));
    }

    @PostMapping("/email/valid/code")
    @Operation(summary = "发送忘记密码邮箱验证码 @author wangzh")
    @NoNeedLogin
    public ResponseDTO<String> sendEmailValidCode(@RequestBody @Validated EmailSendRequest request) {
        return ResponseDTO.success(ResponseDTO.SUCCESS_CODE, userService.sendEmailValidCode(request));
    }
}
