package wang.zehui.self.cook.book.common.enums;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.base.CaseFormat;

import java.util.LinkedHashMap;
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

    static String getInfo(Class<? extends BaseEnum> clazz) {
        BaseEnum[] enums = clazz.getEnumConstants();
        LinkedHashMap<String, JSONObject> json = new LinkedHashMap<>(enums.length);
        for (BaseEnum e : enums) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", e.getValue());
            jsonObject.put("desc", e.getDescription());
            json.put(e.toString(), jsonObject);
        }

        String enumJson = JSON.toJSONString(json);
        enumJson = enumJson.replaceAll("\"", "");
        enumJson = enumJson.replaceAll("\t", "&nbsp;&nbsp;");
        enumJson = enumJson.replaceAll("\n", "<br>");
        String prefix = "  <br>  export const " + CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, clazz.getSimpleName() + " = <br> ");
        return prefix + enumJson + " <br>";
    }
}
