package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author wangzehui
 * @Date 2026/4/2 15:55
 */
@Data
public class UserUpdateRequest {

    @Schema(description = "用户id")
    private String userId;
}
