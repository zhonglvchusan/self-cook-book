package wang.zehui.self.cook.book.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.common.utils.ResponseUtil;
import wang.zehui.self.cook.book.domain.request.FilePageRequest;
import wang.zehui.self.cook.book.domain.response.FileDownloadResponse;
import wang.zehui.self.cook.book.domain.response.FileResponse;
import wang.zehui.self.cook.book.domain.response.FileUploadResponse;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.service.IFileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

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

    @Operation(summary = "获取文件URL: 根据fileKey @author wangzh")
    @GetMapping("/url")
    public ResponseDTO<String> getFileUrl(String fileKey) {
        List<FileResponse> fileUrls = fileService.getFileUrls(Collections.singletonList(fileKey));
        return ResponseDTO.success(ResponseDTO.SUCCESS_CODE, fileUrls.get(0).getFileUrl());
    }

    @Operation(summary = "下载文件流: 根据fileKey @author wangzh")
    @GetMapping("/download")
    public void download(String fileKey, HttpServletResponse response) throws IOException {
        FileDownloadResponse downloadFile = fileService.getDownloadFile(fileKey);
        ResponseUtil.setDownloadFileHeader(response, downloadFile.getFileName(), downloadFile.getFileSize());
        response.getOutputStream().write(downloadFile.getData());
    }

    @Operation(summary = "获取文件列表: 分页 @author wangzh")
    @GetMapping
    @SaCheckPermission("file:list")
    public ResponseDTO<PageResult<FileResponse>> getFilePage(FilePageRequest request) {
        return ResponseDTO.success(fileService.getFilePage(request));
    }

    @Operation(summary = "删除文件 @author wangzh")
    @DeleteMapping
    @SaCheckPermission("file:delete")
    public ResponseDTO<Boolean> deleteFile(String fileKey) {
        return ResponseDTO.success(fileService.deleteFile(fileKey));
    }
}

