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
@NoArgsConstructor
@AllArgsConstructor
public class CodeInfoVO {

    private Integer codeId;
    private String codeText;
    private String codeType;
    private String codeTitle;
    private String codeCreateDate;
    private Integer codeAuthorId;

    private String userAccount;
    private String userName;
}
