package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.dao.RoleDao;
import wang.zehui.self.cook.book.domain.entity.Role;
import wang.zehui.self.cook.book.domain.request.RoleRequest;
import wang.zehui.self.cook.book.domain.request.RoleSearchRequest;
import wang.zehui.self.cook.book.domain.response.RoleInfoResponse;
import wang.zehui.self.cook.book.domain.response.RoleListResponse;
import wang.zehui.self.cook.book.service.IRoleMenuService;
import wang.zehui.self.cook.book.service.IRoleService;
import org.springframework.stereotype.Service;
import wang.zehui.self.cook.book.service.IRoleUserService;

import java.util.Objects;

/**
 * 角色菜单表(Role)表服务实现类
 *
 * @author wangzehui
 * @since 2026-04-10 10:03:49
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements IRoleService {

    @Autowired
    private IRoleUserService roleUserService;

    @Autowired
    private IRoleMenuService roleMenuService;

    @Override
    public Boolean saveOrUpdateRole(RoleRequest request) {
        Role role = new Role();
        if (!StringUtils.isBlank(request.getId())) {
            role = this.getById(request.getId());
            if (Objects.isNull(role)) {
                throw new BusinessException("角色不存在");
            }
        }

        Role dbRole = this.getOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getRoleCode, request.getRoleName())
                .last("LIMIT 1"));
        if (!Objects.isNull(dbRole) && !dbRole.getId().equals(role.getId())) {
            throw new BusinessException("角色名已存在");
        }

        dbRole = this.getOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getRoleCode, request.getRoleCode())
                .last("LIMIT 1"));
        if (!Objects.isNull(dbRole) && !dbRole.getId().equals(role.getId())) {
            throw new BusinessException("角色编码已存在");
        }

        BeanUtils.copyProperties(request, role);
        return this.saveOrUpdate(role);
    }

    @Override
    @Transactional(rollbackFor = { Error.class, Exception.class, BusinessException.class})
    public Boolean deleteRole(String roleId) {
        Role role = this.getById(roleId);
        if (Objects.isNull(role)) {
            throw new BusinessException("角色不存在");
        }

        roleUserService.removeByRoleId(roleId);
        roleMenuService.removeByRoleId(roleId);

        return this.removeById(roleId);
    }

    @Override
    public PageResult<RoleListResponse> getRoleList(RoleSearchRequest request) {
        Page<Role> page = new Page<>(request.getPageNum(), request.getPageSize());
        this.page(page, Wrappers.<Role>lambdaQuery()
                .like(!StringUtils.isBlank(request.getRoleName()), Role::getRoleName, request.getRoleName())
                .like(!StringUtils.isBlank(request.getRoleCode()), Role::getRoleCode, request.getRoleCode()));

        return PageResult.of(page, PageResult.easyBeanCopyFunction(RoleListResponse::new));
    }

    @Override
    public RoleInfoResponse getRoleInfo(String roleId) {
        Role role = this.getById(roleId);
        if (Objects.isNull(role)) {
            throw new BusinessException("角色不存在");
        }

        RoleInfoResponse response = new RoleInfoResponse();
        BeanUtils.copyProperties(role, response);
        return response;
    }
}

