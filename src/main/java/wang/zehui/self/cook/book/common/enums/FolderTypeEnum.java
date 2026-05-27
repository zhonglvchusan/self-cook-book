package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wangzehui
 * @Date 2026/5/27 12:19
 */
@AllArgsConstructor
@Getter
public enum FolderTypeEnum implements BaseEnum {

    COMMON(0, "/common/", "通用"),


    ;

    private final Integer value;

    private final String folderPath;

    private final String description;
}
