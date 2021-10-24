package com.mewhz.paste.controller;

import com.mewhz.paste.model.Code;
import com.mewhz.paste.model.CodeHtml;
import com.mewhz.paste.utils.HtmlCreateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Controller
public class CodeController {

    @RequestMapping("/code")
//    @ResponseBody
    public String code(@RequestParam String text, @RequestParam String type){
        Date date = new Date();
        Code code = new Code(text, type, date);
        fun(code);
        return "redirect:/heart?id="+code.getDate().getTime();
    }

    public void fun(Code code){
        File file = new File("src/main/resources/static/data/"+code.getDate().getTime()+".txt");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] bytes = code.getText().getBytes(StandardCharsets.UTF_8);
            for (byte b : bytes) {
                out.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }}
}
