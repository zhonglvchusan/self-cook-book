package wang.zehui.self.cook.book.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import wang.zehui.self.cook.book.common.enums.ErrorCodeEnum;

/**
 * @Author wangzehui
 * @Date 2025/11/5 12:16
 */
@Data
@Schema
public class ResponseDTO<T> {

    public static final int SUCCESS_CODE = 0;

    public static final int ERROR_CODE = 500;

    public static final String SUCCESS_MESSAGE = "操作成功";

    public static final String ERROR_MESSAGE = "操作失败";

    @Schema(description = "状态码")
    private Integer code;

    @Schema(description = "是否成功")
    private Boolean success;

    @Schema(description = "状态信息")
    private String message;

    @Schema(description = "返回数据")
    private T data;

    public ResponseDTO(Integer code, Boolean success, String message, T data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ResponseDTO(Integer code, Boolean success, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }

    public static <T> ResponseDTO<T> success() {
        return new ResponseDTO<>(SUCCESS_CODE, true, SUCCESS_MESSAGE);
    }

    public static <T> ResponseDTO<T> success(String message) {
        return new ResponseDTO<>(SUCCESS_CODE, true, message);
    }

    public static <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO<>(SUCCESS_CODE, true, SUCCESS_MESSAGE, data);
    }

    public static <T> ResponseDTO<T> success(Integer code, T data) {
        return new ResponseDTO<>(code, true, SUCCESS_MESSAGE, data);
    }

    public static <T> ResponseDTO<T> error() {
        return new ResponseDTO<>(ERROR_CODE, false, ERROR_MESSAGE);
    }

    public static <T> ResponseDTO<T> error(String message) {
        return new ResponseDTO<>(ERROR_CODE, false, message);
    }

    public static <T> ResponseDTO<T> error(Integer code, String message) {
        return new ResponseDTO<>(code, false, message);
    }

    public static <T> ResponseDTO<T> error(ErrorCodeEnum errorCodeEnum) {
        return new ResponseDTO<>(errorCodeEnum.getCode(), false, errorCodeEnum.getMessage());
    }
}
