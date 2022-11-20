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
public class CodeStatusInfo {

    private Integer userId;
    private String  userName;
    private String  userAccount;

    private Byte statusType;

    private Integer codeId;
    private String codeTitle;
    private String statusCreateDate;
}
