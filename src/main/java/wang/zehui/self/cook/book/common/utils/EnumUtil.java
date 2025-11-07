package wang.zehui.self.cook.book.common.utils;

import wang.zehui.self.cook.book.common.enums.BaseEnum;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Author wangzehui
 * @Date 2025/11/7 14:35
 */
public class EnumUtil {

    /**
     * @Description: 根据值获取枚举对象
     * @param value 枚举值
     * @param enumClass 枚举类
     * @Return: T
     * @Author: wangzehui
     * @Date: 2025/11/7 14:37
     */
    public static <T extends BaseEnum> T getEnumByValue(Object value, Class<T> enumClass) {
        if (Objects.isNull(value)) {
            return null;
        }

        return Stream.of(enumClass.getEnumConstants())
                .filter(e -> e.equalsValue(value))
                .findFirst()
                .orElse(null);
    }
}
