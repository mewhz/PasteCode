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
public class CodeSubmitVO {

    private String codeText;
    private String codeType;
    private String codeTitle;
    private Integer codeAuthorId;
}
