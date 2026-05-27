package wang.zehui.self.cook.book.service.file;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import wang.zehui.self.cook.book.common.consts.DateFormatConst;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.SystemEnvironment;
import wang.zehui.self.cook.book.common.enums.FolderTypeEnum;
import wang.zehui.self.cook.book.domain.response.FileDownloadResponse;
import wang.zehui.self.cook.book.domain.response.FileUploadResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author wangzehui
 * @Date 2026/5/27 11:35
 */
@Slf4j
@Service
public class FileCloudStorageServiceImpl implements IFileStorageService {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private SystemEnvironment systemEnvironment;

    @Value("${file.rustfs.bucketName:self-cook}")
    private String bucketName;

    @Value("${file.rustfs.urlPrefix}")
    private String urlPrefix;

    /**
     * 自定义元数据 文件名称
     */
    private static final String USER_METADATA_FILE_NAME = "file-name";

    /**
     * 自定义元数据 文件格式
     */
    private static final String USER_METADATA_FILE_FORMAT = "file-format";

    /**
     * 自定义元数据 文件大小
     */
    private static final String USER_METADATA_FILE_SIZE = "file-size";

    @Override
    public FileUploadResponse upload(MultipartFile file, FolderTypeEnum folderTypeEnum) {
        String originalFileName = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFileName)) {
            throw new BusinessException("上传文件名为空");
        }

        String fileType = FilenameUtils.getExtension(originalFileName);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String time = DateFormatUtils.format(new Date(), DateFormatConst.NORM_DATETIME_SPLIT);
        String fileKey = systemEnvironment.getCurrentEnvironment().getValue() + folderTypeEnum.getFolderPath() + uuid + "_" + time + "." + fileType;

        // 文件名称 URL 编码
        String urlEncoderFileName;
        try {
            urlEncoderFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            log.error("文件上传——文件名称 URL 编码失败", e);
            throw new BusinessException("上传失败");
        }

        Map<String, String> userMetaData = new HashMap<>(5);
        userMetaData.put(USER_METADATA_FILE_NAME, urlEncoderFileName);
        userMetaData.put(USER_METADATA_FILE_FORMAT, fileType);
        userMetaData.put(USER_METADATA_FILE_SIZE, String.valueOf(file.getSize()));

        // 构建请求
        PutObjectRequest request = PutObjectRequest.builder()
                .metadata(userMetaData)
                .bucket(bucketName)
                .key(fileKey)
                .contentType(this.getContentType(fileType))
                .contentLength(file.getSize())
                .build();

        PutObjectResponse putObjectResponse;
        // 开始上传文件
        try {
             putObjectResponse = s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
        } catch (IOException e) {
            log.error("文件上传——文件上传至s3时出错", e);
            throw new BusinessException("上传失败");
        }
        log.info("文件上传成功，返回结果：{}", JSON.toJSONString(putObjectResponse));

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(originalFileName);
        response.setFileType(fileType);
        response.setFileUrl(urlPrefix + fileKey);
        response.setFileKey(fileKey);
        response.setFileSize(file.getSize());

        return response;
    }

    @Override
    public String getFileUrl(String fileKey) {
        return "";
    }

    @Override
    public FileDownloadResponse download(String fileKey) {
        return null;
    }

    @Override
    public Boolean delete(String fileKey) {
        return null;
    }
}
