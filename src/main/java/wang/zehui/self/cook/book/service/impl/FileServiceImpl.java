package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.common.enums.FolderTypeEnum;
import wang.zehui.self.cook.book.common.utils.EnumUtil;
import wang.zehui.self.cook.book.common.utils.FileUtil;
import wang.zehui.self.cook.book.dao.FileDao;
import wang.zehui.self.cook.book.domain.entity.File;
import wang.zehui.self.cook.book.domain.request.FilePageRequest;
import wang.zehui.self.cook.book.domain.response.FileDownloadResponse;
import wang.zehui.self.cook.book.domain.response.FileResponse;
import wang.zehui.self.cook.book.domain.response.FileUploadResponse;
import wang.zehui.self.cook.book.service.IFileService;
import org.springframework.stereotype.Service;
import wang.zehui.self.cook.book.service.file.IFileStorageService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 文件信息表(File)表服务实现类
 *
 * @author wangzehui
 * @since 2026-05-27 12:16:59
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileDao, File> implements IFileService {

    @Autowired
    private IFileStorageService fileStorageService;

    /**
     * 文件名最大长度
     */
    private static final int FILE_NAME_MAX_LENGTH = 100;

    @Override
    public FileUploadResponse uploadFile(MultipartFile file, Integer folderType) {
        FolderTypeEnum folderTypeEnum = EnumUtil.getEnumByValue(folderType, FolderTypeEnum.class);
        if (Objects.isNull(folderTypeEnum)) {
            throw new BusinessException("文件夹错误");
        }

        if (Objects.isNull(file) || file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            throw new BusinessException("上传文件名不能为空");
        }

        if (originalFilename.length() > FILE_NAME_MAX_LENGTH) {
            throw new BusinessException("上传文件名过长, 最大长度为:" + FILE_NAME_MAX_LENGTH);
        }

        // 校验文件类型是否允许上传
        String fileMimeType = FileUtil.getFileMimeType(file);
        if (!FileUtil.checkMimeType(fileMimeType)) {
            throw new BusinessException("不支持上传的文件类型");
        }

        // 上传文件
        FileUploadResponse uploadResponse = fileStorageService.upload(file, folderTypeEnum);

        File fileEntity = new File();
        fileEntity.setFolderType(folderType);
        fileEntity.setFileName(uploadResponse.getFileName());
        fileEntity.setFileSize(uploadResponse.getFileSize());
        fileEntity.setFileKey(uploadResponse.getFileKey());
        fileEntity.setFileType(uploadResponse.getFileType());
        this.save(fileEntity);

        uploadResponse.setId(fileEntity.getId());

        return uploadResponse;
    }

    @Override
    public List<FileResponse> getFileUrls(List<String> fileKeys) {
        if (CollectionUtils.isEmpty(fileKeys)) {
            return Collections.emptyList();
        }

        // 查询数据库，并获取 file url
        List<File> files = this.list(Wrappers.<File>lambdaQuery().in(File::getFileKey, fileKeys));
        Map<String, FileResponse> fileMap = files.stream()
                .map(file -> {
                    FileResponse response = new FileResponse();
                    BeanUtils.copyProperties(file, response);
                    return response;
                })
                .collect(Collectors.toMap(FileResponse::getFileKey, Function.identity()));
        for (FileResponse file : fileMap.values()) {
            String fileUrl = fileStorageService.getFileUrl(file.getFileKey());
            file.setFileUrl(fileUrl);
        }

        return new ArrayList<>(fileMap.values());
    }

    @Override
    public FileDownloadResponse getDownloadFile(String fileKey) {
        File file = this.getOne(Wrappers.<File>lambdaQuery()
                .eq(File::getFileKey, fileKey));
        if (Objects.isNull(file)) {
            throw new BusinessException("文件不存在");
        }

        return fileStorageService.download(fileKey);
    }

    @Override
    public PageResult<FileResponse> getFilePage(FilePageRequest request) {
        LambdaQueryWrapper<File> queryWrapper = Wrappers.<File>lambdaQuery()
                .eq(!Objects.isNull(request.getFolderType()), File::getFolderType, request.getFolderType())
                .like(!StringUtils.isBlank(request.getFileName()), File::getFileName, request.getFileName())
                .like(!StringUtils.isBlank(request.getFileKey()), File::getFileKey, request.getFileKey())
                .like(!StringUtils.isBlank(request.getFileType()), File::getFileType, request.getFileType())
                .ge(!Objects.isNull(request.getStartTime()), File::getCreateTime, request.getStartTime())
                .le(!Objects.isNull(request.getEndTime()), File::getCreateTime, request.getEndTime());

        Page<File> page = new Page<>(request.getPageNum(), request.getPageSize());
        this.page(page, queryWrapper);

        return PageResult.of(page, PageResult.easyBeanCopyFunction(FileResponse::new));
    }

    @Override
    public Boolean deleteFile(String fileKey) {
        fileStorageService.delete(fileKey);
        return this.removeById(fileKey);
    }

}

