package com.mewhz.paste.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mewhz
 * 用户注册时发送的请求
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterVO {

    private String userName;
    private String userPassword;
    private String userEmail;
}
