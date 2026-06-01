package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.common.enums.ErrorCodeEnum;
import wang.zehui.self.cook.book.common.enums.UserAdminFlagEnum;
import wang.zehui.self.cook.book.common.utils.ConvertUtil;
import wang.zehui.self.cook.book.common.utils.RequestUtil;
import wang.zehui.self.cook.book.dao.UserDao;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.ChangePasswordRequest;
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

    @Value("${common.initPassword:abc@123}")
    private String initPassword;

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
            throw new BusinessException(ErrorCodeEnum.LOGIN_NAME_EXIST);
        }

        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);

        String password = userAddRequest.getLoginPassword();
        // 没有填写注册密码
        if (StringUtils.isBlank(password)) {
            // 没有openId，是后台创建用户，使用默认密码
            if (StringUtils.isBlank(userAddRequest.getOpenId())) {
                password = initPassword;
            } else {
                password = RandomStringUtils.randomAlphanumeric(8);
            }
        }

        user.setLoginPassword(DigestUtils.md5Hex(password + user.getLoginName()));

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

        return this.removeById(userId);
    }

    @Override
    public Boolean changeUserState(String userId) {
        User user = this.getById(userId);
        if (Objects.isNull(user)) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_EXIST);
        }

        user.setState(Objects.equals(0, user.getState()) ? 1 : 0);
        return this.updateById(user);
    }

    @Override
    public Boolean updateUser(UserUpdateRequest userUpdateRequest) {
        User user = this.getById(userUpdateRequest.getUserId());
        BeanUtils.copyProperties(userUpdateRequest, user);
        user.setLoginPassword(null);

        return this.updateById(user);
    }

    @Override
    public PageResult<UserListResponse> getUserList(UserListRequest request) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery()
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
            throw new BusinessException(ErrorCodeEnum.USER_NOT_EXIST);
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

    @Override
    public Boolean changePassword(ChangePasswordRequest request) {
        User user = this.getById(RequestUtil.getUserId());

        if (Objects.isNull(user)) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_EXIST);
        }

        if (user.getDeleted()) {
            throw new BusinessException(ErrorCodeEnum.USER_DELETED);
        }

        if (!Objects.equals(0, user.getState())) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_ACTIVE);
        }

        // 校验原密码是否正确
        String oldPasswordHex = DigestUtils.md5Hex(request.getOldPassword() + user.getLoginName());
        if (!Objects.equals(user.getLoginPassword(), oldPasswordHex)) {
            throw new BusinessException(ErrorCodeEnum.BEFORE_PASSWORD_ERROR);
        }

        // 校验是否一样的密码
        String newPasswordHex = DigestUtils.md5Hex(request.getNewPassword() + user.getLoginName());
        if (Objects.equals(user.getLoginPassword(), newPasswordHex)) {
            throw new BusinessException(ErrorCodeEnum.PASSWORD_NOT_CHANGE);
        }

        return this.update(Wrappers.<User>lambdaUpdate()
                .eq(User::getId, user.getId())
                .set(User::getLoginPassword, newPasswordHex));
    }

    @Override
    public Boolean resetPassword(String userId) {
        User user = this.getById(userId);

        if (Objects.isNull(user)) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_EXIST);
        }

        if (user.getDeleted()) {
            throw new BusinessException(ErrorCodeEnum.USER_DELETED);
        }

        if (!Objects.equals(0, user.getState())) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_ACTIVE);
        }

        return this.update(Wrappers.<User>lambdaUpdate()
                .eq(User::getId, user.getId())
                .set(User::getLoginPassword, DigestUtils.md5Hex(initPassword + user.getLoginName())));
    }
}

