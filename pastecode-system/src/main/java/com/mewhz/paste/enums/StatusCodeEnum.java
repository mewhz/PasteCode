package com.mewhz.paste.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mewhz
 */
@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    // 成功时返回的状态码和信息说明
    SUCCESS(20000, "操作成功"),

    // 失败时返回的状态码和信息说明
    FAIL(51000, "操作失败"),

    SYSTEM_ERROR(50000, "系统异常");

    private final Integer code;

    private final String message;
}
