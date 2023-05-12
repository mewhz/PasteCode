package com.mewhz.paste.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.mewhz.paste.constant.LogConstant.*;

/**
 * @author mewhz
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogSearchVO {

    private String logType;

    private String startDate;
    private String endDate;

    private Long current;

    public Long getCurrent() {
        return current * LOG_PAGE_NUM;
    }
}
