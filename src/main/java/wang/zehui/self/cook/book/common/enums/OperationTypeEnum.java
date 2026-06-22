package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wangzehui
 * @Date 2026/6/22 15:01
 */
@AllArgsConstructor
@Getter
public enum OperationTypeEnum implements BaseEnum {

    PLUS(0, "增加"),

    SUBTRACT(1, "减少"),

    ;

    private final Integer value;

    private final String description;
}
