package wang.zehui.self.cook.book.common.domain;

import lombok.Data;

import java.util.List;

/**
 * @Author wangzehui
 * @Date 2026/4/17 14:40
 */
@Data
public class UserPermission {

    /**
     * 用户权限列表
     */
    private List<String> permissions;

    /**
     * 用户角色列表
     */
    private List<String> roles;
}
