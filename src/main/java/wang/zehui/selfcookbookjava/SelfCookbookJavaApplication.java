package wang.zehui.selfcookbookjava;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableApolloConfig
public class SelfCookbookJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfCookbookJavaApplication.class, args);
    }

}
