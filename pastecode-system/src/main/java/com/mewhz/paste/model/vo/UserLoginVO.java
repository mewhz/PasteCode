package com.mewhz.paste.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mewhz
 */
@Data
@NoArgsConstructor
public class UserLoginVO {

    private String userAccount;
    private String userPassword;
}
