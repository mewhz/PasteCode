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
@TableName("status")
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    @TableId(value = "status_id", type = IdType.AUTO)
    private Integer statusId;
    private Integer codeId;
    private Integer userId;
    private Byte statusType;
    private String statusCreateDate;
}
