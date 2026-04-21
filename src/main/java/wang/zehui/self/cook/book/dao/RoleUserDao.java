package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wang.zehui.self.cook.book.domain.entity.RoleUser;
import wang.zehui.self.cook.book.domain.entity.User;
import wang.zehui.self.cook.book.domain.request.RoleUserRequest;
import wang.zehui.self.cook.book.domain.response.RoleInfoResponse;

import java.util.List;

/**
 * 角色用户关系表(RoleUser)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-04-13 11:45:33
 */
@Mapper
public interface RoleUserDao extends BaseMapper<RoleUser> {

    /**
     * @Description: 搜索角色下用户列表
     * @param request 搜索条件
     * @param page 分页参数
     * @Return: java.util.List<wang.zehui.self.cook.book.domain.entity.User>
     * @Author: wangzehui
     * @Date: 2026/4/17 9:58
     */
    Page<User> getRoleUserByName(@Param("request") RoleUserRequest request, @Param("page") Page<User> page);

    /**
     * @Description: 获取用户角色列表
     * @param userId 用户id
     * @Return: java.util.List<wang.zehui.self.cook.book.domain.response.RoleInfoResponse>
     * @Author: wangzehui
     * @Date: 2026/4/17 10:46
     */
    List<RoleInfoResponse> getRoleByUserId(@Param("userId") String userId);
}

