package wang.zehui.self.cook.book.service.impl;

import org.springframework.stereotype.Service;
import wang.zehui.self.cook.book.domain.request.LoginRequest;
import wang.zehui.self.cook.book.domain.request.UserRequest;
import wang.zehui.self.cook.book.domain.response.LoginResultResponse;
import wang.zehui.self.cook.book.service.ILoginService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author wangzehui
 * @Date 2025/10/31 14:37
 */
@Service
public class ILoginServiceImpl implements ILoginService {

    @Override
    public LoginResultResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public UserRequest getLoginUser(String loginId, HttpServletRequest request) {
        return null;
    }
}
