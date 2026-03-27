package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.UserAddRequest;

/**
 * (User)表服务接口
 *
 * @author wangzehui
 * @since 2025-10-29 16:59:43
 */
public interface IUserService extends IService<User> {

    /**
     * @Description: 通过登录账号获取用户信息
     * @param loginName 登录账号
     * @Return: wang.zehui.self.cook.book.domain.entity.User
     * @Author: wangzehui
     * @Date: 2026/3/26 16:59
     */
    User getByLoginName(String loginName);

    /**
     * @Description: 注册用户
     * @param userAddRequest
     * @Return: java.lang.String
     * @Author: wangzehui
     * @Date: 2026/3/26 17:25
     */
    String registerUser(UserAddRequest userAddRequest);

}

