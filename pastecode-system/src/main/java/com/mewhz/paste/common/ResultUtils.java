package com.mewhz.paste.common;

/**
 * 返回工具类
 * @author mewhz
 */
public class ResultUtils {

    /**
     * 成功结果
     * @param responseData 返回的数据
     * @param <T> 数据的类型
     * @return 结果集
     */
    public static <T> BaseResponse<T> success(T responseData) {
        return new BaseResponse<>(0, responseData, "ok");
    }

//    public static <T> BaseResponse<T> success() {
//
//    }

    /**
     * 登录错误
     * @param responseMessage 返回的错误信息
     * @return 错误代码与错误信息
     */
    public static <T> BaseResponse<T> loginError(String responseMessage) {
        return new BaseResponse<>(40010, responseMessage);
    }

    public static <T> BaseResponse<T> codeError(String responseMessage) {
        return new BaseResponse<>(40020, responseMessage);
    }

    public static <T> BaseResponse<T> registerError(String responseMessage) {
        return new BaseResponse<>(40030, responseMessage);
    }

    /**
     * 未知错误
     * @return 错误代码与未知错误
     */
    public static <T> BaseResponse<T> error(String responseMessage) {
        return new BaseResponse<>(50000, responseMessage);
    }
}
