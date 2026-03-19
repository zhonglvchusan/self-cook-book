package wang.zehui.self.cook.book.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import wang.zehui.self.cook.book.SelfCookbookApiApplicationTests;
import wang.zehui.self.cook.book.common.consts.HeaderConst;
import wang.zehui.self.cook.book.domain.request.UserRequest;

/**
 * @Author wangzehui
 * @Date 2026/3/19 15:27
 */
public class LoginServiceTest extends SelfCookbookApiApplicationTests {

    @Autowired
    private ILoginService loginService;

    @Test
    public void getLoginUserTest() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader(HeaderConst.MINI_APP, true);
        String loginId = "admin:1";
        UserRequest userRequest = loginService.getLoginUser(loginId, mockHttpServletRequest);
        super.printResult(userRequest);
    }
}
