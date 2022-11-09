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
@TableName("user")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String userAccount;
    private String userPassword;


}
