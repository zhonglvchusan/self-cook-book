package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author wangzehui
 * @Date 2025/10/28 15:27
 */
@Data
@Schema
public class UserRequest {

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "管理员标识 0: 超级管理员 1: 管理员 2: 普通用户")
    private Integer adminFlag;
}
