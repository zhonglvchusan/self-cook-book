package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.CheckEnum;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.LaunchTypeEnum;

import javax.validation.constraints.Max;

/**
 * @Author wangzehui
 * @Date 2026/7/31 10:27
 */
@Data
public class DishLaunchRequest {

    @Schema(description = "菜品id")
    private String dishId;

    @SchemaEnum(value = LaunchTypeEnum.class, description = "上架状态")
    @CheckEnum(value = LaunchTypeEnum.class, message = "上架状态错误")
    @Max(value = 1, message = "上架状态错误")
    private Integer launchType;
}
