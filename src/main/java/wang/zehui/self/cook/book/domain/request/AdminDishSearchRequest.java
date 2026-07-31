package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.zehui.self.cook.book.common.annoation.CheckEnum;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.domain.PageRequest;
import wang.zehui.self.cook.book.common.enums.LaunchTypeEnum;

/**
 * @Author wangzehui
 * @Date 2026/7/30 15:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminDishSearchRequest extends PageRequest {

    @Schema(description = "菜品id")
    private String id;

    @Schema(description = "餐厅id")
    private String restaurantId;

    @SchemaEnum(value = LaunchTypeEnum.class, description = "上架状态")
    @CheckEnum(value = LaunchTypeEnum.class, message = "上架状态错误")
    private Integer launchFlag;
}
