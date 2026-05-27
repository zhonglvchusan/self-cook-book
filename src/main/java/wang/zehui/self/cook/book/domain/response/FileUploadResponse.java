package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author wangzehui
 * @Date 2026/5/27 12:18
 */
@Data
public class FileUploadResponse {

    @Schema(description = "文件id")
    private String id;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件url")
    private String fileUrl;

    @Schema(description = "文件key")
    private String fileKey;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "文件大小")
    private Long fileSize;
}
