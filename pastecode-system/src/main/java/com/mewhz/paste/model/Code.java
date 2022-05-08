package com.mewhz.paste.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author mewhz
 */
@Data
@ToString
@TableName("code")
@AllArgsConstructor
@NoArgsConstructor
public class Code {

    @TableId(value = "code_id", type = IdType.AUTO)
    private Integer codeId;
    private String codeText;
    private String codeType;
    private String codeDate;
}
