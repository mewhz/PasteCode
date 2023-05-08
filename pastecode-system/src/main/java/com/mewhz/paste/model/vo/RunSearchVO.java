package com.mewhz.paste.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.mewhz.paste.constant.RunConstant.RUN_PAGE_NUM;

/**
 * @author mewhz
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RunSearchVO {

    private String codeType;

    private String userAccount;

    private String startDate;
    private String endDate;

    private Long current;

    public Long getCurrent() {
        return current * RUN_PAGE_NUM;
    }
}
