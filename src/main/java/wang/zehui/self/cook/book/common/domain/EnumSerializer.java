package wang.zehui.self.cook.book.common.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import wang.zehui.self.cook.book.common.annoation.EnumSerialize;
import wang.zehui.self.cook.book.common.consts.StringConst;
import wang.zehui.self.cook.book.common.enums.BaseEnum;
import wang.zehui.self.cook.book.common.utils.EnumUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author wangzehui
 * @Date 2026/4/8 14:50
 */
public class EnumSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    private Class<? extends BaseEnum> enumClass;

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(value);
        String fieldName = jsonGenerator.getOutputContext().getCurrentName() + "Desc";
        Object desc;
        // 多个枚举类 逗号分割
        if (value instanceof String && String.valueOf(value).contains(StringConst.COMMA)) {
            desc = Arrays.stream(String.valueOf(value).split(StringConst.COMMA))
                    .map(e -> {
                        int code = Integer.parseInt(e);
                        return EnumUtil.getEnumDescriptionByValue(code, enumClass);
                    }).collect(Collectors.toList());
        } else {
            BaseEnum enumByValue = EnumUtil.getEnumByValue(value, enumClass);
            desc = Objects.isNull(enumByValue) ? null : enumByValue.getDescription();
        }

        jsonGenerator.writeObjectField(fieldName, desc);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        EnumSerialize annotation = beanProperty.getAnnotation(EnumSerialize.class);
        if (Objects.isNull(annotation)) {
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        enumClass = annotation.value();
        return this;
    }
}
