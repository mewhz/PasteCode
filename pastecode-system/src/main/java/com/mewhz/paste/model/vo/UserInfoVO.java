package com.mewhz.paste.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author mewhz
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {

    private Integer userId;
    private String userName;
    private String userAccount;
    private String userEmail;
    private Integer userCodeNum;
    private String userAvatar;
    private Integer userRole;

    private String userRoleStr;
}
