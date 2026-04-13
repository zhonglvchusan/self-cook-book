package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author wangzehui
 * @Date 2026/4/10 15:17
 */
@Data
public class RoleMenuTreeResponse {

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "已选菜单ID")
    private List<String> selectedMenuIds;

    @Schema(description = "菜单树")
    private List<MenuSimpleTreeResponse> menuTree;
}
