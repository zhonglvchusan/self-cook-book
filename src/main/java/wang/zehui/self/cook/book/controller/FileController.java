package wang.zehui.self.cook.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wang.zehui.self.cook.book.common.utils.ResponseUtil;
import wang.zehui.self.cook.book.domain.response.FileDownloadResponse;
import wang.zehui.self.cook.book.domain.response.FileResponse;
import wang.zehui.self.cook.book.domain.response.FileUploadResponse;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;
import wang.zehui.self.cook.book.service.IFileService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @Author wangzehui
 * @Date 2026/6/5 16:29
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
}
