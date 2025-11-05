package wang.zehui.self.cook.book.utils;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author wangzehui
 * @Date 2025/11/5 11:53
 */
@Slf4j
public class ResponseUtil {

    public static void write(HttpServletResponse response, ResponseDTO<?> responseDTO) {
        // 重置response头
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try {
            response.getWriter().write(JSON.toJSONString(responseDTO));
            response.flushBuffer();
        } catch (IOException e) {
            log.error("write response error", e);
            throw new RuntimeException(e);
        }

    }
}
