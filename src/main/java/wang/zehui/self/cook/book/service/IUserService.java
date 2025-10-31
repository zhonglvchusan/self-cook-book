package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.UserRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * (User)表服务接口
 *
 * @author wangzehui
 * @since 2025-10-29 16:59:43
 */
public interface IUserService extends IService<User> {

    /**
     * @Description:
     * @param loginId
     * @param request
     * @Return: wang.zehui.self.cook.book.domain.request.UserRequest
     * @Author: wangzehui
     * @Date: 2025/10/31 14:37
     */
    UserRequest getLoginUser(String loginId, HttpServletRequest request);

}

