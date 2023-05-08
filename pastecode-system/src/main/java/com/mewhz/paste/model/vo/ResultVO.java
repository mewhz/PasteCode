package com.mewhz.paste.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.mewhz.paste.enums.StatusCodeEnum.*;

/**
 * @author mewhz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultVO<T> {

    private Boolean flag;

    private Integer code;

    private String message;

    private T data;

    public ResultVO(T data) {
        this.data = data;
    }

    public static <T> ResultVO<T> ok() {
        return resultVO(true, SUCCESS.getCode(), SUCCESS.getMessage(), null);
    }

    public static <T> ResultVO<T> ok(T data) {
        return resultVO(true, SUCCESS.getCode(), SUCCESS.getMessage(), data);
    }

    public static <T> ResultVO<T> ok(T data, String message) {
        return resultVO(true, SUCCESS.getCode(), message, data);
    }

    public static <T> ResultVO<T> fail() {
        return resultVO(false, FAIL.getCode(), FAIL.getMessage(), null);
    }

    public static <T> ResultVO<T> fail(T data) {
        return resultVO(false, FAIL.getCode(), FAIL.getMessage(),  data);
    }

    public static <T> ResultVO<T> fail(T data, String message) {
        return resultVO(false, FAIL.getCode(), message, data);
    }

    public static <T> ResultVO<T> fail(Integer code, String message) {
        return resultVO(false, code, message, null);
    }

    private static <T> ResultVO<T> resultVO(Boolean flag, Integer code, String message, T data) {
        return ResultVO.<T>builder()
                .flag(flag)
                .code(code)
                .message(message)
                .data(data).build();
    }

}
