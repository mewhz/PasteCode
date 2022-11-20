package com.mewhz.paste.common;

import lombok.Data;

/**
 * 通用返回类
 * @author mewhz
 */
@Data
public class BaseResponse<T> {

    private int responseCode;
    private T responseData;
    private String responseMessage;

    public BaseResponse() {

    }

    public BaseResponse(int responseCode, T responseData, String responseMessage) {
        this.responseCode = responseCode;
        this.responseData = responseData;
        this.responseMessage = responseMessage;
    }

    public BaseResponse(int responseCode, T responseData) {
        this(responseCode, responseData, "");
    }

    public BaseResponse(int responseCode, String responseMessage) {
        this(responseCode, null, responseMessage);
    }




}
