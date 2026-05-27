package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import wang.zehui.self.cook.book.common.enums.FolderTypeEnum;
import wang.zehui.self.cook.book.domain.entity.File;
import wang.zehui.self.cook.book.domain.response.FileUploadResponse;

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

}

