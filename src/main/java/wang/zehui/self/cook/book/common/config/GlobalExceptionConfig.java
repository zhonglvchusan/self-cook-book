package wang.zehui.self.cook.book.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.SystemEnvironment;
import wang.zehui.self.cook.book.domain.response.ResponseDTO;

import java.util.stream.Collectors;

/**
 * @Author wangzehui
 * @Date 2026/4/2 11:43
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionConfig {

    @Autowired
    private SystemEnvironment systemEnvironment;

    /**
     * @Description: 业务异常捕获
     * @param e 业务异常
     * @Return: wang.zehui.self.cook.book.domain.response.ResponseDTO<?>
     * @Author: wangzehui
     * @Date: 2026/4/2 11:50
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseDTO<?> businessExceptionHandler(Exception e) {
        if (!systemEnvironment.isProd()) {
            log.error("全局业务异常,URL: {}", getCurrentRequestUrl(), e);
        }

        return ResponseDTO.error(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDTO<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("参数校验异常,URL: {}", getCurrentRequestUrl(), e);
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseDTO.error(message);
    }

    /**
     * @Description: 其他全部异常
     * @param e 异常
     * @Return: wang.zehui.self.cook.book.domain.response.ResponseDTO<?>
     * @Author: wangzehui
     * @Date: 2026/4/2 11:50
     */
    @ExceptionHandler(Throwable.class)
    public ResponseDTO<?> errorHandler(Throwable e) {
        log.error("捕获全局异常,URL: {}", getCurrentRequestUrl(), e);
        return ResponseDTO.error(systemEnvironment.isProd() ? null : e.toString());
    }

    /**
     * 获取当前请求url
     */
    private String getCurrentRequestUrl() {
        RequestAttributes request = RequestContextHolder.getRequestAttributes();
        if (null == request) {
            return null;
        }
        ServletRequestAttributes servletRequest = (ServletRequestAttributes) request;
        return servletRequest.getRequest().getRequestURI();
    }
}
