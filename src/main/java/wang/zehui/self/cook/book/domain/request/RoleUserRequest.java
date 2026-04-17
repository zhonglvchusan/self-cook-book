package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.zehui.self.cook.book.common.domain.PageRequest;

/**
 * @Author wangzehui
 * @Date 2026/4/15 10:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleUserRequest extends PageRequest {

    @Schema(description = "角色id")
    private String roleId;

    @Schema(description = "搜索关键词")
    private String keyword;
}
