package com.mewhz.paste.utils;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.mewhz.paste.model.Code;

import java.sql.SQLException;
import java.util.List;

public class CodeSQL {
    public CodeSQL (){

    }
    public void insertCode(Code code){
        try {
            Db.use().insert(
                    Entity.create("code")
                    .set("text", code.getText())
                    .set("time_id", code.getDate().getTime()+"")
                    .set("type", code.getType())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
}
