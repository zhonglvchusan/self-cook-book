package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.zehui.self.cook.book.common.domain.PageRequest;

/**
 * @Author wangzehui
 * @Date 2026/7/30 14:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminCommentSearchRequest extends PageRequest {

    @Schema(description = "餐厅id")
    private String restaurantId;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;
}
