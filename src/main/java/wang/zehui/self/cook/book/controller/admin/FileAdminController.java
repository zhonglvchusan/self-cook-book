package wang.zehui.self.cook.book.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.request.FilePageRequest;
import wang.zehui.self.cook.book.domain.response.FileResponse;
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
public class FileAdminController {

    @Resource
    private IFileService fileService;

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

