package com.mewhz.paste.model.vo;

import lombok.Data;

/**
 * @author mewhz
 */
@Data
public class UserLoginResponse {
    private Integer id;
    private String userName;
    private String userAccount;

    private String tokenName;
    private String tokenValue;
}
