package com.mewhz.paste.model.dto;

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
public class LogTypeInfoTotalDTO {

    private Long logTypeTotal;
    private String logType;
}
