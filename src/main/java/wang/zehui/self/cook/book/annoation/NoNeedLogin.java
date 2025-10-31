package wang.zehui.self.cook.book.annoation;

import java.lang.annotation.*;

/**
 * @Author wangzehui
 * @Date 2025/10/28 14:25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoNeedLogin {
}
