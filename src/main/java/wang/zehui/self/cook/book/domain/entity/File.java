package wang.zehui.self.cook.book.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件信息表(File)表实体类
 *
 * @author wangzehui
 * @since 2026-05-27 12:16:59
 */
@Data
@TableName("t_file")
public class File {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 文件夹类型
     */
    private Integer folderType;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件key 用于文件下载
     */
    private String fileKey;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUserId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}

