package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wangzehui
 * @Date 2026/3/24 10:30
 */
@AllArgsConstructor
@Getter
public enum LoginDeviceEnum implements BaseEnum {

    PC(0, "电脑端"),

    H5(1, "H5"),

    MINI_APP(2, "小程序"),

    ;

    private final Integer type;

    private final String description;

    @Override
    public Object getValue() {
        return type;
    }

    public static LoginDeviceEnum getEnumByType(Integer type) {
        for (LoginDeviceEnum value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
