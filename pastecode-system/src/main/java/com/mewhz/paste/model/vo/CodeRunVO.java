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
public class CodeRunVO {

    private String codeText;
    private String codeType;
    private Integer codeAuthorId;

    private String runInput;
}
