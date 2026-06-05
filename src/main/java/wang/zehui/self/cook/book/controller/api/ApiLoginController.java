package wang.zehui.self.cook.book.controller.api;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wang.zehui.self.cook.book.common.annoation.NoNeedLogin;
import wang.zehui.self.cook.book.common.utils.RequestUtil;
import wang.zehui.self.cook.book.domain.request.WxLoginRequest;
import wang.zehui.self.cook.book.domain.request.WxPhoneLoginRequest;
import wang.zehui.self.cook.book.domain.response.LoginResultResponse;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.service.ILoginService;

/**
 * @Author wangzehui
 * @Date 2026/6/4 15:14
 */
@Tag(name = "登录相关接口")
@RestController
@RequestMapping("/login")
public class ApiLoginController {

    @Autowired
    private ILoginService loginService;

    @PostMapping
    @Operation(summary = "微信登录 @author wangzh")
    @NoNeedLogin
    public ResponseDTO<LoginResultResponse> wxLogin(@RequestBody @Validated WxLoginRequest loginRequest) {
        return ResponseDTO.success(loginService.wxLogin(loginRequest));
    }

    @PostMapping("/phone")
    @Operation(summary = "微信手机号登录 @author wangzh")
    @NoNeedLogin
    public ResponseDTO<LoginResultResponse> wxPhoneLogin(@RequestBody @Validated WxPhoneLoginRequest request) {
        return ResponseDTO.success(loginService.wxPhoneLogin(request));
    }

    @GetMapping("/info")
    @Operation(summary = "获取登录信息 @author wangzh")
    public ResponseDTO<LoginResultResponse> getLoginInfo() {
        String tokenValue = StpUtil.getTokenValue();
        LoginResultResponse loginResult = loginService.getLoginResult(RequestUtil.getUserRequest());
        loginResult.setToken(tokenValue);
        return ResponseDTO.success(loginResult);
    }

    @GetMapping("/logout")
    @Operation(summary = "退出登录 @author wangzh")
    public ResponseDTO<Boolean> logout() {
        return ResponseDTO.success(loginService.logout(RequestUtil.getUserRequest()));
    }

}
