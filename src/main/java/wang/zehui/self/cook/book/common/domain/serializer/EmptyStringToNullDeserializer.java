package wang.zehui.self.cook.book.common.domain.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
* @Author wangzehui
* @Date 2026/4/21 11:08
*/
public class EmptyStringToNullDeserializer extends StringDeserializer {


    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = super.deserialize(p, ctxt);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return value;
    }
}
