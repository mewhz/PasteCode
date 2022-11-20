package com.mewhz.paste.main.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCodeEnum {
    SUCCESS(0, "操作成功"),

    NO_LOGIN(10, "登录失败");

    private final Integer code;

    private final String message;
}
