package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author wangzehui
 * @Date 2026/5/27 14:15
 */
@Data
public class FileDownloadResponse {

    @Schema(description = "文件数据")
    private byte[] data;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件大小")
    private Long fileSize;

    @Schema(description = "文件格式")
    private String fileFormat;
}
