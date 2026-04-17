package wang.zehui.self.cook.book.service;

import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.LoginRequest;
import wang.zehui.self.cook.book.domain.request.UserRequest;
import wang.zehui.self.cook.book.domain.response.LoginResultResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author wangzehui
 * @Date 2025/10/31 14:37
 */
public interface ILoginService {

    /**
     * @Description: 登录系统
     * @param loginRequest 登录参数
     * @Return: wang.zehui.self.cook.book.domain.response.LoginResultResponse
     * @Author: wangzehui
     * @Date: 2025/11/6 12:00
     */
    LoginResultResponse login(LoginRequest loginRequest);

    /**
     * @Description:
     * @param loginId
     * @param request
     * @Return: wang.zehui.self.cook.book.domain.request.UserRequest
     * @Author: wangzehui
     * @Date: 2025/10/31 14:37
     */
    UserRequest getLoginUser(String loginId, HttpServletRequest request);

    /**
     * @Description: 载入用户登录信息
     * @param user 数据库user信息
     * @Return: wang.zehui.self.cook.book.domain.request.UserRequest
     * @Author: wangzehui
     * @Date: 2026/3/19 14:31
     */
    UserRequest loadLoginInfo(User user);

    /**
     * @Description: 退出登录
     * @param userRequest 用户信息
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/4/17 15:36
     */
    Boolean logout(UserRequest userRequest);

}
