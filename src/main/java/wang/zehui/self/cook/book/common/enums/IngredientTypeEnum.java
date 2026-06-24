package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wangzehui
 * @Date 2026/6/24 11:11
 */
@AllArgsConstructor
@Getter
public enum IngredientTypeEnum implements BaseEnum {

    SYSTEM_INGREDIENT(0, "系统食材"),

    USER_INGREDIENT(1, "用户食材");

    ;

    private final Integer value;

    private final String description;
}
