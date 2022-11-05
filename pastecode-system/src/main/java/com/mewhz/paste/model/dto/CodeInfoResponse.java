package com.mewhz.paste.model.dto;

import lombok.Data;

/**
 * @author mewhz
 */
@Data
public class CodeInfoResponse {

    private Integer codeId;
    private String codeText;
    private String codeType;
    private String codeTitle;

    private String  userAccount;
    private String  codeInfoDate;
}
