package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @Author wangzehui
 * @Date 2026/4/17 10:25
 */
@Data
public class RoleUserUpdateRequest {

    @Schema(description = "角色id")
    @NotBlank(message = "角色id不能为空")
    private String roleId;

    @Schema(description = "用户id集合")
    @NotEmpty(message = "用户id集合不能为空")
    private Set<String> userIds;

}
