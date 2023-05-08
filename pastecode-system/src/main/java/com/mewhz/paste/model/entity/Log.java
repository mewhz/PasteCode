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
@TableName("log")
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;
    private String logType;
    private String logInfo;
    private Boolean logIsSuccess;
    private String logCreateDate;
}
