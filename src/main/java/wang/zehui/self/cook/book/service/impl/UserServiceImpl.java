package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.dao.UserDao;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.UserAddRequest;
import wang.zehui.self.cook.book.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * (User)表服务实现类
 *
 * @author wangzehui
 * @since 2025-10-29 16:59:43
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

    @Override
    public User getByLoginName(String loginName) {
        return this.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getLoginName, loginName)
                .last("LIMIT 1"));
    }

    @Override
    public String registerUser(UserAddRequest userAddRequest) {
        User userDb = this.getByLoginName(userAddRequest.getLoginName());
        if (!Objects.isNull(userDb)) {
            throw new BusinessException("登录名重复");
        }

        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);

        // 没有填写注册密码
        if (StringUtils.isBlank(userAddRequest.getLoginPassword())) {
            String password = RandomStringUtils.randomAlphanumeric(8);
            user.setLoginPassword(DigestUtils.md5Hex(password + user.getLoginName()));
        }

        user.setDeleted(false);
        this.save(user);

        return user.getId();
    }
}

