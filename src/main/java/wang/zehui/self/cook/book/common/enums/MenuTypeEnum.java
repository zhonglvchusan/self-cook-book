package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wangzehui
 * @Date 2026/4/9 12:18
 */
@AllArgsConstructor
@Getter
public enum MenuTypeEnum implements BaseEnum {

    CATALOG(0, "目录"),

    MENU(1, "菜单"),

    POINTS(2, "功能点"),

    ;

    private final Integer code;

    private final String description;

    @Override
    public Object getValue() {
        return code;
    }

}
