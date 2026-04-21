package wang.zehui.self.cook.book.common.domain.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Objects;

/**
 * @Author wangzehui
 * @Date 2026/4/21 10:53
 */
public class LongJsonSerializer extends JsonSerializer<Long> {

    public static final LongJsonSerializer INSTANCE = new LongJsonSerializer();

    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(value)) {
            jsonGenerator.writeNull();
            return;
        }

        String longStr = String.valueOf(value);
        if (longStr.length() > 16) {
            jsonGenerator.writeString(longStr);
        } else {
            jsonGenerator.writeNumber(value);
        }
    }
}
