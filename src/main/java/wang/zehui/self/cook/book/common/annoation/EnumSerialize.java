package wang.zehui.self.cook.book.common.annoation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import wang.zehui.self.cook.book.common.domain.EnumSerializer;
import wang.zehui.self.cook.book.common.enums.BaseEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author wangzehui
 * @Date 2026/4/8 14:46
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = EnumSerializer.class, nullsUsing = EnumSerializer.class)
public @interface EnumSerialize {

    Class<? extends BaseEnum> value();
}
