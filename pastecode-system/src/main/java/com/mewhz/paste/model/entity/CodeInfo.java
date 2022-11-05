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
@TableName("code_info")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CodeInfo {

    @TableId(value = "code_info_id", type = IdType.AUTO)
    private Integer codeInfoId;
    private Integer codeId;
    private String  userAccount;
    private String  codeInfoDate;
}
