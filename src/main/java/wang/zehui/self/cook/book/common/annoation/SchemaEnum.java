package wang.zehui.self.cook.book.common.annoation;

import wang.zehui.self.cook.book.common.enums.BaseEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author wangzehui
 * @Date 2026/3/24 11:04
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SchemaEnum {

    /**
     * 枚举类对象
     *
     */
    Class<? extends BaseEnum> value();

    String example() default "";

    boolean hidden() default false;

    boolean required() default false;

    String dataType() default "";

    String description() default "";
}
