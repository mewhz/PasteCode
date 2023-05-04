package com.mewhz.paste.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author mewhz
 */
@Data
@ToString
@TableName("run")
@AllArgsConstructor
@NoArgsConstructor
public class Run {

    @TableId(value = "run_id", type = IdType.AUTO)
    private Integer runId;
    private Integer codeId;
    private String runInput;
    private String runOutput;
    private String runError;
    private String runCreateDate;
}
