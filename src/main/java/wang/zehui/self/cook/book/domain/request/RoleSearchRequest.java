package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.zehui.self.cook.book.common.domain.PageRequest;

/**
 * @Author wangzehui
 * @Date 2026/4/10 10:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleSearchRequest extends PageRequest {

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色编码")
    private String roleCode;
}
