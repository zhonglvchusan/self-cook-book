package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wangzehui
 * @Date 2026/7/30 15:21
 */
@Getter
@AllArgsConstructor
public enum LaunchTypeEnum implements BaseEnum {

    NORMAL(0, "上架"),

    TAKE_DOWN(1, "下架"),

    ADMIN_TAKE_DOWN(2, "管理员下架"),

    ;

    private final Integer value;

    private final String description;
}
