package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.FolderTypeEnum;

import java.time.LocalDateTime;

/**
 * @Author wangzehui
 * @Date 2026/5/28 10:08
 */
@Data
public class FileResponse {

    @Schema(description = "文件id")
    private String id;

    @Schema(description = "文件夹类型")
    @SchemaEnum(value = FolderTypeEnum.class)
    private Integer folderType;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件大小")
    private Long fileSize;

    @Schema(description = "文件key")
    private String fileKey;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "文件url")
    private String fileUrl;

}
