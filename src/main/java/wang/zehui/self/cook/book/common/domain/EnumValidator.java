package wang.zehui.self.cook.book.common.domain;

import wang.zehui.self.cook.book.common.annoation.CheckEnum;
import wang.zehui.self.cook.book.common.enums.BaseEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: 枚举校验器
 * @Author wangzehui
 * @Date 2026/3/24 11:29
 */
public class EnumValidator implements ConstraintValidator<CheckEnum, Object> {

    private List<Object> enumVals;

    private boolean required;

    @Override
    public void initialize(CheckEnum constraintAnnotation) {
        required = constraintAnnotation.required();
        Class<? extends BaseEnum> enumClass = constraintAnnotation.value();
        enumVals = Stream.of(enumClass.getEnumConstants()).map(BaseEnum::getValue).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return !required;
        }

        if (value instanceof List) {
            return this.checkList((List<Object>) value);
        }

        return enumVals.contains(value);
    }

    /**
     * 校验集合类型
     *
     */
    private boolean checkList(List<Object> list) {
        if (required && list.isEmpty()) {
            // 必须的情况下 list 不能为空
            return false;
        }
        // 校验是否重复
        long count = list.stream().distinct().count();
        if (count != list.size()) {
            return false;
        }
        return enumVals.containsAll(list);
    }
}
