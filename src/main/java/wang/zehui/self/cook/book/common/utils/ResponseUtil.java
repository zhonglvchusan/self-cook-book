package wang.zehui.self.cook.book.common.utils;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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

    public static void setDownloadFileHeader(HttpServletResponse response, String fileName, Long fileSize) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        if (!Objects.isNull(fileSize)) {
            response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize));
        }

        if (!StringUtils.isBlank(fileName)) {
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaTypeFactory.getMediaType(fileName).orElse(MediaType.APPLICATION_OCTET_STREAM) + ";charset=utf-8");
            try {
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20"));
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
            // 设置前端可访问的请求头
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        }
    }
}
