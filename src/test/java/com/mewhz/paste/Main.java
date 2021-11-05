package com.mewhz.paste;

import cn.hutool.db.Entity;
import com.mewhz.paste.utils.CodeSQL;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CodeSQL codeSQL = new CodeSQL();
        List<Entity> codes = codeSQL.findIdentifyingCode("6666");
        for (Entity e:codes){
            System.out.println(e.get("time_id"));
        }
    }
}
