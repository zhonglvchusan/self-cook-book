package wang.zehui.self.cook.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wang.zehui.self.cook.book.domain.entity.File;

/**
 * 文件信息表(File)表数据库访问层
 *
 * @author wangzehui
 * @since 2026-05-27 12:16:59
 */
@Mapper
public interface FileDao extends BaseMapper<File> {
}

