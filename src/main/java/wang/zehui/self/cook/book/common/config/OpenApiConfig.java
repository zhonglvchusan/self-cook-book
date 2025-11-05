package wang.zehui.self.cook.book.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wang.zehui.self.cook.book.common.consts.HeaderConst;
import wang.zehui.self.cook.book.common.consts.SwaggerTagConst;
import wang.zehui.self.cook.book.common.domain.SelfOperationCustomizer;

/**
 * springdoc-openapi配置
 * nginx配置前缀时，如果需要访问 [/swagger-ui/index.html] 需添加额外nginx配置
 * location /v3/api-docs/ {
 *     proxy_pass http//domain:port/v3/api-docs/;
 * }
 *
 * @Author wangzehui
 * @Date 2025/11/5 15:25
 */
@Configuration
@Slf4j
public class OpenApiConfig {

    public static final String[] OPEN_API_WHITELIST = {
            "/swagger-ui/**",
            "/swagger-ui/index.html",
            "/swagger-ui.html",
            "/swagger-ui.html/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/doc.html",
    };

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .components(components())
                .info(new Info()
                        .title("个人餐单 V1.x 接口文档")
                        .contact(new Contact().name("王泽辉").email("3364462922@qq.com").url("https://zehui.wang"))
                        .version("v1.x")
                        .description("个人餐单 V1.x 接口文档"))
                .addSecurityItem(new SecurityRequirement().addList(HeaderConst.TOKEN));
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes(HeaderConst.TOKEN, new SecurityScheme().scheme("Bearer").description("请输入token,格式为[Bearer xxxxxxxx]").type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name(HeaderConst.TOKEN));
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("后台管理接口")
                .pathsToMatch("/**")
                .pathsToExclude(SwaggerTagConst.API_PREFIX + "/**")
                .addOperationCustomizer(new SelfOperationCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi apiApi() {
        return GroupedOpenApi.builder()
                .group("api接口")
                .pathsToMatch(SwaggerTagConst.API_PREFIX + "/**")
                .addOperationCustomizer(new SelfOperationCustomizer())
                .build();
    }
}
