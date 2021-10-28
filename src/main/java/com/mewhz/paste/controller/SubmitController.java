package com.mewhz.paste.controller;

import com.mewhz.paste.model.Code;
import com.mewhz.paste.utils.CodeSQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class SubmitController {

    @RequestMapping("/submit")
//    @ResponseBody
    public String code(@RequestParam String text, @RequestParam String type){
        Date date = new Date();
        Code code = new Code(text, type, date);
        System.out.println(code);
        CodeSQL codeSql = new CodeSQL();
        codeSql.insertCode(code);
        return "redirect:/code?id=" + code.getDate().getTime();
    }
}
