package wang.zehui.self.cook.book.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author wangzehui
 * @Date 2026/5/27 16:28
 */
@Slf4j
public class FileUtil {

    // 定义白名单MIME类型
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            "application/json",
            "application/zip",
            "application/x-7z-compressed",
            "application/pdf",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/vnd.ms-powerpoint",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-works",
            "text/csv",
            "audio/*",
            "video/*",
            // 图片类型 svg有安全隐患，所以不使用"image/*"
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/bmp"
    );

    /**
     * @Description: 获取文件的 MIME 类型
     * @param file 文件
     * @Return: java.lang.String
     * @Author: wangzehui
     * @Date: 2026/5/27 16:37
     */
    public static String getFileMimeType(MultipartFile file) {
        try {
            TikaConfig tika = new TikaConfig();
            Metadata metadata = new Metadata();
            metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, file.getOriginalFilename());
            TikaInputStream stream = TikaInputStream.get(file.getInputStream());
            MediaType mimetype = tika.getDetector().detect(stream, metadata);
            return mimetype.toString();
        } catch (IOException | TikaException e) {
            log.error(e.getMessage(), e);
            return MimeTypes.OCTET_STREAM;
        }
    }

    /**
     * @Description: 检查文件的 MIME 类型是否在白名单中
     * @param mimeType MIME类型
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2026/5/27 16:43
     */
    public static boolean checkMimeType(String mimeType) {
        return ALLOWED_MIME_TYPES.stream().anyMatch(allowedType -> matchesMimeType(mimeType, allowedType));
    }

    /**
     * @Description: 检查文件的 MIME 类型是否与指定的 MIME 类型匹配 （支持通配符）
     * @param fileType 文件的 MIME 类型
     * @param mimeType MIME类型（支持通配符）
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2026/5/27 16:41
     */
    public static boolean matchesMimeType(String fileType, String mimeType) {
        if (mimeType.endsWith("/*")) {
            String prefix = mimeType.substring(0, mimeType.length() - 1);
            return fileType.startsWith(prefix);
        } else {
            return fileType.equalsIgnoreCase(mimeType);
        }
    }
}
