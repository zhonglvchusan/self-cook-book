package wang.zehui.self.cook.book.common.domain;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.stereotype.Component;
import wang.zehui.self.cook.book.common.annoation.CheckEnum;
import wang.zehui.self.cook.book.common.annoation.SchemaEnum;
import wang.zehui.self.cook.book.common.enums.BaseEnum;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * @Author wangzehui
 * @Date 2026/3/24 11:18
 */
@Component
public class SchemaEnumPropertyCustomizer implements PropertyCustomizer {

    @Override
    public Schema customize(Schema property, AnnotatedType type) {
        if (Objects.isNull(type.getCtxAnnotations())) {
            return property;
        }

        StringBuilder description = new StringBuilder();
        for (Annotation ctxAnnotation : type.getCtxAnnotations()) {
            if (ctxAnnotation.annotationType().equals(CheckEnum.class) && ((CheckEnum) ctxAnnotation).required()) {
                description.append("<font style=\"color: red\">【必填】</font>");
            }
        }

        for (Annotation ctxAnnotation : type.getCtxAnnotations()) {
            if (ctxAnnotation.annotationType().equals(SchemaEnum.class)) {
                description.append(((SchemaEnum) ctxAnnotation).description());
                Class<? extends BaseEnum> clazz = ((SchemaEnum) ctxAnnotation).value();
                description.append(BaseEnum.getInfo(clazz));
            }
        }

        if (description.length() > 0) {
            property.setDescription(description.toString());
        }

        return property;
    }
}
