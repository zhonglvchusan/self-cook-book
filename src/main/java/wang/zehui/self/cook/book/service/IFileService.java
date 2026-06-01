package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.entity.File;
import wang.zehui.self.cook.book.domain.request.FilePageRequest;
import wang.zehui.self.cook.book.domain.response.FileDownloadResponse;
import wang.zehui.self.cook.book.domain.response.FileResponse;
import wang.zehui.self.cook.book.domain.response.FileUploadResponse;

import java.util.List;

/**
 * 文件信息表(File)表服务接口
 *
 * @author wangzehui
 * @since 2026-05-27 12:16:59
 */
public interface IFileService extends IService<File> {

    /**
     * @Description: 上传文件
     * @param file 文件
     * @param folderType 文件夹枚举类型 {@link wang.zehui.self.cook.book.common.enums.FolderTypeEnum}
     * @Return: wang.zehui.self.cook.book.domain.response.FileUploadResponse
     * @Author: wangzehui
     * @Date: 2026/5/27 16:21
     */
    FileUploadResponse uploadFile(MultipartFile file, Integer folderType);

    /**
     * @Description: 根据文件key获取文件url
     * @param fileKeys 文件key
     * @Return: java.util.List<wang.zehui.self.cook.book.domain.response.FileResponse>
     * @Author: wangzehui
     * @Date: 2026/5/28 10:09
     */
    List<FileResponse> getFileUrls(List<String> fileKeys);

    /**
     * @Description: 下载文件
     * @param fileKey 文件key
     * @Return: wang.zehui.self.cook.book.domain.response.FileDownloadResponse
     * @Author: wangzehui
     * @Date: 2026/5/28 11:16
     */
    FileDownloadResponse getDownloadFile(String fileKey);

    /**
     * @Description: 获取文件分页列表
     * @param request 文件分页请求
     * @Return: wang.zehui.self.cook.book.common.domain.PageResult<wang.zehui.self.cook.book.domain.response.FileResponse>
     * @Author: wangzehui
     * @Date: 2026/5/29 15:27
     */
    PageResult<FileResponse> getFilePage(FilePageRequest request);

    /**
     * @Description: 删除文件
     * @param fileKey 文件key
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/6/1 14:54
     */
    Boolean deleteFile(String fileKey);
}

