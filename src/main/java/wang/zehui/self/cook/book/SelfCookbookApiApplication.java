package wang.zehui.self.cook.book;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableApolloConfig
@MapperScan(basePackages = {"wang.zehui.self.cook.book.dao"})
public class SelfCookbookApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfCookbookApiApplication.class, args);
    }

}
