package wang.zehui.self.cook.book.service.file;

import org.springframework.web.multipart.MultipartFile;
import wang.zehui.self.cook.book.common.enums.FolderTypeEnum;
import wang.zehui.self.cook.book.domain.response.FileDownloadResponse;
import wang.zehui.self.cook.book.domain.response.FileUploadResponse;

/**
 * @Author wangzehui
 * @Date 2026/5/27 11:34
 */
public interface IFileStorageService {

    /**
     * @Description: 上传文件
     * @param file 文件
     * @param folderTypeEnum 文件夹类型 {@link wang.zehui.self.cook.book.common.enums.FolderTypeEnum}
     * @Return: wang.zehui.self.cook.book.domain.response.FileUploadResponse
     * @Author: wangzehui
     * @Date: 2026/5/27 14:12
     */
    FileUploadResponse upload(MultipartFile file, FolderTypeEnum folderTypeEnum);

    /**
     * @Description: 获取文件url地址
     * @param fileKey 文件key
     * @Return: java.lang.String
     * @Author: wangzehui
     * @Date: 2026/5/27 14:13
     */
    String getFileUrl(String fileKey);

    /**
     * @Description: 下载文件
     * @param fileKey 文件key
     * @Return: wang.zehui.self.cook.book.domain.response.FileDownloadResponse
     * @Author: wangzehui
     * @Date: 2026/5/27 14:15
     */
    FileDownloadResponse download(String fileKey);

    /**
     * @Description: 删除文件
     * @param fileKey 文件key
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/5/27 14:16
     */
    Boolean delete(String fileKey);

    /**
     * @Description: 获取文件类型
     * @param fileExt 文件后缀
     * @Return: java.lang.String
     * @Author: wangzehui
     * @Date: 2026/5/27 14:16
     */
    default String getContentType(String fileExt) {
        // 文件的后缀名
        if ("bmp".equalsIgnoreCase(fileExt)) {
            return "image/bmp";
        }
        if ("gif".equalsIgnoreCase(fileExt)) {
            return "image/gif";
        }
        if ("jpeg".equalsIgnoreCase(fileExt) || "jpg".equalsIgnoreCase(fileExt)) {
            return "image/jpeg";
        }
        if ("png".equalsIgnoreCase(fileExt)) {
            return "image/png";
        }
        if ("html".equalsIgnoreCase(fileExt)) {
            return "text/html";
        }
        if ("txt".equalsIgnoreCase(fileExt)) {
            return "text/plain";
        }
        if ("vsd".equalsIgnoreCase(fileExt)) {
            return "application/vnd.visio";
        }
        if ("ppt".equalsIgnoreCase(fileExt) || "pptx".equalsIgnoreCase(fileExt)) {
            return "application/vnd.ms-powerpoint";
        }
        if ("doc".equalsIgnoreCase(fileExt) || "docx".equalsIgnoreCase(fileExt)) {
            return "application/msword";
        }
        if ("pdf".equalsIgnoreCase(fileExt)) {
            return "application/pdf";
        }
        if ("xml".equalsIgnoreCase(fileExt)) {
            return "text/xml";
        }
        return "";
    }
}
