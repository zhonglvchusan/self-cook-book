package wang.zehui.self.cook.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;
import wang.zehui.self.cook.book.domain.response.FileUploadResponse;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.service.IFileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 文件信息表(File)表控制层
 *
 * @author wangzehui
 * @since 2026-05-27 12:16:59
 */
@Tag(name = "文件上传相关接口")
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private IFileService fileService;

    @Operation(summary = "上传文件 @author wangzh")
    @PostMapping("/upload")
    public ResponseDTO<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Integer folder) {
        return ResponseDTO.success(fileService.uploadFile(file, folder));
    }
}

