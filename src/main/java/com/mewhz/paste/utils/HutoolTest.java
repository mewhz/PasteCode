package com.mewhz.paste.utils;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;

import java.sql.SQLException;

public class HutoolTest {

    public static void main(String[] args) {
        try {
            System.out.println(Db.use().findAll("code"));
            Db.use().insert(
                    Entity.create("code")
                    .set("text", "print(\"Hello World\")")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
