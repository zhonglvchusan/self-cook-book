package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author wangzehui
 * @Date 2026/4/10 14:53
 */
@Data
public class RoleMenuUpdateRequest {

    @Schema(description = "角色ID")
    @NotBlank(message = "角色ID不能为空")
    private String roleId;

    @Schema(description = "菜单ID列表")
    @NotEmpty(message = "菜单ID列表不能为空")
    private List<String> menuIds;
}
