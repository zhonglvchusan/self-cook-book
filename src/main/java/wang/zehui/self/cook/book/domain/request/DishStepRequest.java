package wang.zehui.self.cook.book.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author wangzehui
 * @Date 2026/6/23 14:20
 */
@Data
public class DishStepRequest {

    @Schema(description = "菜品步骤id")
    private String id;

    @Schema(description = "菜品id")
    @JsonIgnore
    private String dishId;

    @Schema(description = "步骤几")
    @NotBlank(message = "步骤不能为空")
    private Integer stepNumber;

    @Schema(description = "步骤标题")
    @NotBlank(message = "步骤标题不能为空")
    private String title;

    @Schema(description = "步骤内容")
    private String content;

    @Schema(description = "步骤图片")
    private String imageUrl;

    @Schema(description = "预计耗时(秒)")
    private Integer duration;

    @Schema(description = "小贴士")
    private String tip;
}
