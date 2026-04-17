package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.common.utils.SpringContextUtil;
import wang.zehui.self.cook.book.dao.RoleUserDao;
import wang.zehui.self.cook.book.domain.entity.RoleUser;
import wang.zehui.self.cook.book.domain.request.RoleUserRequest;
import wang.zehui.self.cook.book.domain.request.RoleUserUpdateRequest;
import wang.zehui.self.cook.book.domain.response.RoleInfoResponse;
import wang.zehui.self.cook.book.domain.response.UserListResponse;
import wang.zehui.self.cook.book.service.IRoleUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色用户关系表(RoleUser)表服务实现类
 *
 * @author wangzehui
 * @since 2026-04-13 11:45:33
 */
@Service
public class RoleUserServiceImpl extends ServiceImpl<RoleUserDao, RoleUser> implements IRoleUserService {

    @Autowired
    private RoleUserDao roleUserDao;

    @Override
    public PageResult<UserListResponse> getUserPageList(RoleUserRequest request) {
        Page<RoleUser> page = new Page<>(request.getPageNum(), request.getPageSize());
        roleUserDao.getRoleUserByName(request, page);

        return PageResult.of(page, PageResult.easyBeanCopyFunction(UserListResponse::new));
    }

    @Override
    public Boolean deleteRoleUserBatch(RoleUserUpdateRequest request) {
        return this.remove(Wrappers.<RoleUser>lambdaQuery()
                .eq(RoleUser::getRoleId, request.getRoleId())
                .in(RoleUser::getUserId, request.getUserIds()));
    }

    @Override
    public Boolean roleUserAddBatch(RoleUserUpdateRequest request) {
        String roleId = request.getRoleId();
        Set<String> userIds = request.getUserIds();

        // 改角色下以及存在的用户
        Set<String> dbUserIds = this.list(Wrappers.<RoleUser>lambdaQuery()
                        .eq(RoleUser::getRoleId, roleId)
                        .select(RoleUser::getUserId))
                .stream()
                .map(RoleUser::getUserId)
                .collect(Collectors.toSet());

        Set<String> addUserIds = userIds.stream().filter(id -> !dbUserIds.contains(id)).collect(Collectors.toSet());

        // 需要添加，再添加
        if (!CollectionUtils.isEmpty(addUserIds)) {
            List<RoleUser> addRoleUser = addUserIds.stream()
                    .map(id -> {
                        RoleUser roleUser = new RoleUser();
                        roleUser.setRoleId(roleId);
                        roleUser.setUserId(id);
                        return roleUser;
                    }).collect(Collectors.toList());
            return SpringContextUtil.getBean(IRoleUserService.class).saveBatch(addRoleUser);
        }
        return true;
    }

    @Override
    public List<RoleInfoResponse> getRoleByUserId(String userId) {
        return roleUserDao.getRoleByUserId(userId);
    }

    @Override
    public Boolean existsByRoleId(String roleId) {
        List<RoleUser> roleUsers = this.list(Wrappers.<RoleUser>lambdaQuery()
                .eq(RoleUser::getRoleId, roleId));
        return !CollectionUtils.isEmpty(roleUsers);
    }

    @Override
    public void removeByRoleId(String roleId) {
        // 当角色下没有用户时才可以删除
        if (this.existsByRoleId(roleId)) {
            throw new BusinessException("角色下有用户，请先删除角色下用户");
        }

        this.remove(Wrappers.<RoleUser>lambdaQuery()
                .eq(RoleUser::getRoleId, roleId));
    }
}

