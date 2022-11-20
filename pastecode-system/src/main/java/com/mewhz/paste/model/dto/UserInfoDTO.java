package com.mewhz.paste.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mewhz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    private Integer userId;
    private String userName;
    private String userAccount;
    private String userEmail;
    private Integer userCodeNum;
    private String userAvatar;
    private Integer userRole;
}
