package wang.zehui.self.cook.book.common.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import wang.zehui.self.cook.book.common.domain.SystemEnvironment;
import wang.zehui.self.cook.book.common.enums.SystemEnvironmentEnum;
import wang.zehui.self.cook.book.common.utils.EnumUtil;

import java.util.Objects;

/**
 * @Author wangzehui
 * @Date 2025/11/7 14:11
 */
@Configuration
public class SystemEnvironmentConfig implements Condition {

    @Value("${spring.profiles.active}")
    private String systemEnvironment;

    @Value("${spring.application.name}")
    private String projectName;

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return this.isDevOrTest(context);
    }

    private boolean isDevOrTest(ConditionContext context) {
        String property = context.getEnvironment().getProperty("spring.profiles.active");
        return StringUtils.isNotBlank(property) && (SystemEnvironmentEnum.TEST.equalsValue(property) || SystemEnvironmentEnum.DEV.equalsValue(property));
    }

    @Bean("systemEnvironment")
    public SystemEnvironment initEnvironment() {
        SystemEnvironmentEnum currentEnvironment = EnumUtil.getEnumByValue(systemEnvironment, SystemEnvironmentEnum.class);
        if (Objects.isNull(currentEnvironment)) {
            throw new ExceptionInInitializerError("无法获取当前环境！请在 application.yaml 配置参数: spring.profiles.active");
        }
        if (StringUtils.isBlank(projectName)) {
            throw new ExceptionInInitializerError("无法获取当前项目名称！请在 application.yaml 配置参数 spring.application.name");
        }
        return new SystemEnvironment(currentEnvironment == SystemEnvironmentEnum.PROD, projectName, currentEnvironment);
    }
}
