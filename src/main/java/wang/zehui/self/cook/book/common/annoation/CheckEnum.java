package wang.zehui.self.cook.book.common.annoation;

import wang.zehui.self.cook.book.common.domain.EnumValidator;
import wang.zehui.self.cook.book.common.enums.BaseEnum;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author wangzehui
 * @Date 2026/3/24 11:23
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface CheckEnum {

    /**
     * 默认的错误提示信息
     *
     */
    String message();

    /**
     * 枚举类对象
     * 必须实现 {@link wang.zehui.self.cook.book.common.enums.BaseEnum }接口
     */
    Class<? extends BaseEnum> value();

    /**
     * 是否必填
     *
     */
    boolean required() default false;

    //Class<?>[] groups() default {};

    //Class<? extends Payload>[] payload() default {};
}
