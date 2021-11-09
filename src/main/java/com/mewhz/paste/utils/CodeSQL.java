package com.mewhz.paste.utils;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.mewhz.paste.model.Code;
import com.mewhz.paste.model.IdentifyingCode;

import java.sql.SQLException;
import java.util.List;

public class CodeSQL {
    public CodeSQL (){

    }

    /**
        插入代码等信息到数据库
     */
    public void insertCode(Code code){
        try {
            Db.use().insert(
                    Entity.create("code")
                    .set("text", code.getText())
                    .set("time_id", code.getDate().getTime()+"")
                    .set("type", code.getType())
                    .set("date", code.getDate())
                    .set("ip", code.getIp())
                    .set("user_agent", code.getUserAgent())
                    .set("remark", code.getRemark())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  插入代码识别码等信息到数据库
     */
    public void insertIdentifyingCode(IdentifyingCode identifyingCode){
        try{
            Db.use().insert(
                    Entity.create("identifying_code")
                    .set("identifying", identifyingCode.getIdentifying())
                    .set("time_id", identifyingCode.getTimeId())
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据 id 取出 code 数据
     */
    public List<Entity> findCode(String timeId){
        List<Entity> codes = null;
        try {
            codes = Db.use().findAll(
                    Entity.create("code").set("time_id", "= " + timeId)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codes;
    }

    public List<Entity> findIdentifyingCode(String identifying){
        List<Entity> identifyingCodes = null;
        try{
            identifyingCodes = Db.use().query("select text, type, date, code.time_id, remark\n" +
                    "from code inner join identifying_code\n" +
                    "    on code.time_id = identifying_code.time_id\n" +
                    "    where identifying = ?", identifying);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return identifyingCodes;
    }
}
