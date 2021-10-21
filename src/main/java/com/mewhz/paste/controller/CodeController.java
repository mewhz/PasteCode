package com.mewhz.paste.controller;

import com.mewhz.paste.model.Code;
import com.mewhz.paste.model.CodeHtml;
import com.mewhz.paste.utils.HtmlCreateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class CodeController {

    @RequestMapping("/code")
//    @ResponseBody
    public String code(@RequestParam String text, @RequestParam String type){
        Date date = new Date();
        Code code = new Code(text, type, date);
        CodeHtml codeHtml = new CodeHtml(code.getDate().getTime()+"", text, type);
        HtmlCreateUtils utils = new HtmlCreateUtils(codeHtml);
        utils.createHtml();
        System.out.println(date.getTime());
        return "redirect:/code/" + date.getTime() + ".html";
    }
}
