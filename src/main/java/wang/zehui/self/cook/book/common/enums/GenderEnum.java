package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wangzehui
 * @Date 2026/3/26 17:20
 */
@AllArgsConstructor
@Getter
public enum GenderEnum implements BaseEnum {
    UNKNOWN(0, "未知"),

    MALE(1, "男"),

    FEMALE(2, "女"),

    ;

    private final Integer value;

    private final String description;

}
