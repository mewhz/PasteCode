package com.mewhz.paste.model.dto;

import lombok.Data;

/**
 * 用户登录请求
 * @author mewhz
 */
@Data
public class UserLoginRequest {
    private String userAccount;
    private String userPassword;
}
