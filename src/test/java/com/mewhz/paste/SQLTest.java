package com.mewhz.paste;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SQLTest {

    private static DataSource ds = DSFactory.get();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true){
            String timeId = input.next();
            fun(timeId);
        }
    }

    public static void fun(String timeId){
        List<Entity> result = null;
        {
            try {
                result = Db.use(ds).findAll(
                        Entity.create("student").set("id", "= " + timeId));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(result);
    }
}
