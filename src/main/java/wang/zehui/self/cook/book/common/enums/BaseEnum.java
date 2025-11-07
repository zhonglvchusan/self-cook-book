package wang.zehui.self.cook.book.common.enums;

import java.util.Objects;

/**
 * @Author wangzehui
 * @Date 2025/11/7 14:24
 */
public interface BaseEnum {

    Object getValue();

    String getDescription();

    /**
     * @Description: 判断参数是否与枚举类的value相同
     * @param value 枚举类的value
     * @Return: boolean
     * @Author: wangzehui
     * @Date: 2025/11/7 14:32
     */
    default boolean equalsValue(Object value) {
        return Objects.equals(getValue(), value);
    }
}
