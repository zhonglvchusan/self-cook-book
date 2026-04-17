package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.zehui.self.cook.book.domain.request.UserRequest;

import java.util.List;

/**
 * @Author wangzehui
 * @Date 2025/11/6 11:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResultResponse extends UserRequest {

    @Schema(description = "token")
    private String token;

    @Schema(description = "菜单列表")
    private List<MenuInfoResponse> menus;
}
