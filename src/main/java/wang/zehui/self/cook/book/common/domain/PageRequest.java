package wang.zehui.self.cook.book.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author wangzehui
 * @Date 2026/4/8 14:09
 */
@Data
public class PageRequest {

    @Schema(description = "页码")
    private Integer pageNum = 1;

    @Schema(description = "每页数量")
    private Integer pageSize = 20;
}
