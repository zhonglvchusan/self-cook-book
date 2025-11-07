package wang.zehui.self.cook.book.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wang.zehui.self.cook.book.common.enums.SystemEnvironmentEnum;

/**
 * @Author wangzehui
 * @Date 2025/11/7 14:15
 */
@AllArgsConstructor
@Getter
public class SystemEnvironment {

    // 是否生产环境
    private boolean isProd;

    // 项目名称
    private String projectName;

    // 当前环境
    private SystemEnvironmentEnum currentEnvironment;
}
