package wang.zehui.self.cook.book.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wang.zehui.self.cook.book.common.annoation.NoNeedLogin;
import wang.zehui.self.cook.book.common.utils.RequestUtil;
import wang.zehui.self.cook.book.domain.request.LoginRequest;
import wang.zehui.self.cook.book.domain.request.WxLoginRequest;
import wang.zehui.self.cook.book.domain.response.CaptchaResponse;
import wang.zehui.self.cook.book.domain.response.LoginResultResponse;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.service.ICaptchaService;
import wang.zehui.self.cook.book.service.ILoginService;

/**
 * @Author wangzehui
 * @Date 2026/4/2 11:22
 */
@Tag(name = "登录相关接口")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private ICaptchaService captchaService;

    @PostMapping
    @Operation(summary = "登录 @author wangzh")
    @NoNeedLogin
    public ResponseDTO<LoginResultResponse> login(@RequestBody @Validated LoginRequest loginRequest) {
        return ResponseDTO.success(loginService.login(loginRequest));
    }

    @PostMapping("/wx")
    @Operation(summary = "微信登录 @author wangzh")
    @NoNeedLogin
    public ResponseDTO<LoginResultResponse> wxLogin(@RequestBody @Validated WxLoginRequest loginRequest) {
        return ResponseDTO.success(loginService.wxLogin(loginRequest));
    }

    @GetMapping("/info")
    @Operation(summary = "获取登录信息 @author wangzh")
    public ResponseDTO<LoginResultResponse> getLoginInfo() {
        String tokenValue = StpUtil.getTokenValue();
        LoginResultResponse loginResult = loginService.getLoginResult(RequestUtil.getUserRequest());
        loginResult.setToken(tokenValue);
        return ResponseDTO.success(loginResult);
    }

    @GetMapping("/captcha")
    @Operation(summary = "获取验证码 @author wangzh")
    @NoNeedLogin
    public ResponseDTO<CaptchaResponse> getCaptcha() {
        return ResponseDTO.success(captchaService.generateCaptcha());
    }

    @GetMapping("/logout")
    @Operation(summary = "退出登录 @author wangzh")
    public ResponseDTO<Boolean> logout() {
        return ResponseDTO.success(loginService.logout(RequestUtil.getUserRequest()));
    }
}
