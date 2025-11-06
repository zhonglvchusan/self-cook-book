package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import wang.zehui.self.cook.book.dao.UserDao;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author wangzehui
 * @since 2025-10-29 16:59:43
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

}

