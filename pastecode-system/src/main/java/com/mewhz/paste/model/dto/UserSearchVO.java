package com.mewhz.paste.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.mewhz.paste.constant.UserConstant.*;

/**
 * @author mewhz
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchVO {

    private String userName;
    private String userAccount;
    private String userEmail;

    private Long current;

    public Long getCurrent() {
        return current * USER_PAGE_NUM;
    }
}
