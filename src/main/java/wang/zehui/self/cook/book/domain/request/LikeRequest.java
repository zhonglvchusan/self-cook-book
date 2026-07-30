package wang.zehui.self.cook.book.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.CheckEnum;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.LikeTypeEnum;

import javax.validation.constraints.NotBlank;

/**
 * @Author wangzehui
 * @Date 2026/7/30 9:51
 */
@Data
public class LikeRequest {

    @Schema(description = "用户id", hidden = true)
    @JsonIgnore
    private String userId;

    @Schema(description = "点赞的id")
    @NotBlank(message = "点赞的id不能为空")
    private String likeId;

    @SchemaEnum(value = LikeTypeEnum.class, description = "点赞类型")
    @CheckEnum(value = LikeTypeEnum.class, message = "点赞类型错误")
    private Integer likeType;
}
