package com.mewhz.paste.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.mewhz.paste.constant.CodeConstant.*;
/**
 * @author mewhz
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CodeSearchVO {

    private String codeType;
    private String codeTitle;

    private String userAccount;

    private Long current;

    public Long getCurrent() {
        return current * CODE_PAGE_NUM;
    }
}
