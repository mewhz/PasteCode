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
public class RunInfoVO {

    private Integer codeId;
    private String codeText;
    private String codeType;

    private Integer runId;
    private String runInput;
    private String runOutput;
    private String runError;
    private String runCreateDate;

    private String userAccount;
    private String userName;
}
