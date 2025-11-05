package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wangzehui
 * @Date 2025/11/5 14:59
 */
@Getter
@AllArgsConstructor
public enum UserAdminFlagEnum {

    SUPER_ADMIN(0, "超级管理员"),

    ADMIN(1, "管理员"),

    USER(2, "普通用户"),

    ;

    private final Integer code;

    private final String message;
}
