package wang.zehui.self.cook.book.common.properties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author wangzehui
 * @Date 2026/6/5 12:17
 */
@Data
@Component
@ConfigurationProperties(prefix = "common.email.smtp")
public class EmailServerProperties {

    @Schema(description = "邮箱服务器地址")
    private String host;

    @Schema(description = "发件人邮箱")
    private String user;

    @Schema(description = "邮箱密码")
    private String password;
}
