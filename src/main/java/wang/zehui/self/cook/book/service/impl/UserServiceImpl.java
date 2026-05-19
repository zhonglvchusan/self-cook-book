package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.common.enums.UserAdminFlagEnum;
import wang.zehui.self.cook.book.common.utils.ConvertUtil;
import wang.zehui.self.cook.book.dao.UserDao;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.UserAddRequest;
import wang.zehui.self.cook.book.domain.request.UserListRequest;
import wang.zehui.self.cook.book.domain.request.UserUpdateRequest;
import wang.zehui.self.cook.book.domain.response.UserInfoResponse;
import wang.zehui.self.cook.book.domain.response.UserListResponse;
import wang.zehui.self.cook.book.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public Boolean deleteUser(String userId) {
        User user = this.getById(userId);
        if (Objects.isNull(user)) {
            return true;
        }

        user.setDeleted(true);
        return this.updateById(user);
    }

    @Override
    public Boolean changeUserState(String userId) {
        User user = this.getById(userId);
        if (Objects.isNull(user)) {
            throw new BusinessException("用户不存在");
        }

        user.setState(!user.getState());
        return this.updateById(user);
    }

    @Override
    public Boolean updateUser(UserUpdateRequest userUpdateRequest) {
        User user = this.getById(userUpdateRequest.getUserId());
        BeanUtils.copyProperties(userUpdateRequest, user);

        return this.updateById(user);
    }

    @Override
    public PageResult<UserListResponse> getUserList(UserListRequest request) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery()
                .eq(User::getDeleted, false)
                .eq(!StringUtils.isBlank(request.getUserId()), User::getId, request.getUserId())
                .ne(User::getAdminFlag, UserAdminFlagEnum.SUPER_ADMIN)
                .like(!StringUtils.isBlank(request.getLoginName()), User::getLoginName, request.getLoginName())
                .like(!StringUtils.isBlank(request.getNickname()), User::getNickname, request.getNickname())
                .eq(!Objects.isNull(request.getGender()), User::getGender, request.getGender())
                .eq(!Objects.isNull(request.getState()), User::getState, request.getState())
                .eq(!Objects.isNull(request.getAdminFlag()), User::getAdminFlag, request.getAdminFlag());

        Page<User> page = new Page<>(request.getPageNum(), request.getPageSize());
        this.page(page, queryWrapper);

        return PageResult.of(page, PageResult.easyBeanCopyFunction(UserListResponse::new));
    }

    @Override
    public UserInfoResponse getUserInfo(String userId) {
        User user = this.getById(userId);
        if (Objects.isNull(user)) {
            throw new BusinessException("用户不存在");
        }

        List<String> userIds = new ArrayList<>();
        userIds.add(user.getCreateUserId());
        userIds.add(user.getUpdateUserId());
        List<User> users = this.listByIds(userIds);
        Map<String, String> userNameMap = ConvertUtil.convertMap(users, User::getId, User::getRealName);

        UserInfoResponse response = new UserInfoResponse();
        BeanUtils.copyProperties(user, response);
        response.setCreateUserName(userNameMap.getOrDefault(user.getCreateUserId(), null));
        response.setUpdateUserName(userNameMap.getOrDefault(user.getUpdateUserId(), null));

        return response;
    }
}

