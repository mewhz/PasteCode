package com.mewhz.paste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class Heart{

    @RequestMapping("heart")
    @ResponseBody
    public String heart(@RequestParam String id){
        return fun(id);
    }

    public String fun(String id){
        File file = new File("src/main/resources/static/data/"+id+".txt");
        byte[] bytes = new byte[1024 * 1024];
        FileInputStream in = null;
        String text = null;
        try {
            in = new FileInputStream(file);
            while (in.read(bytes) != -1);
            text = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (text != null) {
            text = text.replaceAll("\n", "<br>");
            text = text.replaceAll(" ", "&nbsp;");
        }
        return text;
    }
}