package wang.zehui.self.cook.book.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import wang.zehui.self.cook.book.common.annoation.CheckEnum;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.consts.DateFormatConst;
import wang.zehui.self.cook.book.common.domain.PageRequest;
import wang.zehui.self.cook.book.common.enums.FolderTypeEnum;

import java.time.LocalDateTime;

/**
 * @Author wangzehui
 * @Date 2026/5/29 15:24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FilePageRequest extends PageRequest {

    @SchemaEnum(value = FolderTypeEnum.class)
    @CheckEnum(value = FolderTypeEnum.class, message = "文件夹类型错误")
    private Integer folderType;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件key")
    private String fileKey;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = DateFormatConst.NORM_DATETIME_FORMAT)
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = DateFormatConst.NORM_DATETIME_FORMAT)
    private LocalDateTime endTime;
}
