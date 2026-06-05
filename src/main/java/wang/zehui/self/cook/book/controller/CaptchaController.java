package wang.zehui.self.cook.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.zehui.self.cook.book.common.annoation.NoNeedLogin;
import wang.zehui.self.cook.book.domain.response.CaptchaResponse;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.service.ICaptchaService;

/**
 * @Author wangzehui
 * @Date 2026/6/5 16:09
 */
@Tag(name = "验证码相关接口")
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private ICaptchaService captchaService;

    @GetMapping
    @Operation(summary = "获取验证码 @author wangzh")
    @NoNeedLogin
    public ResponseDTO<CaptchaResponse> getCaptcha() {
        return ResponseDTO.success(captchaService.generateCaptcha());
    }

}
