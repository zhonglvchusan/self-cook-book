package wang.zehui.self.cook.book.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wangzehui
 * @Date 2025/11/7 14:16
 */
@AllArgsConstructor
@Getter
public enum SystemEnvironmentEnum implements BaseEnum {

    DEV(SystemEnvironmentNameConst.DEV, "开发环境"),

    TEST(SystemEnvironmentNameConst.TEST, "测试环境"),

    PRE(SystemEnvironmentNameConst.PRE, "预生产环境"),

    PROD(SystemEnvironmentNameConst.PROD, "生产环境"),

    ;

    private final String value;

    private final String description;

    public static final class SystemEnvironmentNameConst {
        public static final String DEV = "dev";
        public static final String TEST = "test";
        public static final String PRE = "pre";
        public static final String PROD = "prod";
    }

}
