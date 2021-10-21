package com.mewhz.paste.controller;

import com.mewhz.paste.model.Code;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CodeController {

    @RequestMapping("/code")
    @ResponseBody
    public String code(@RequestParam String text, @RequestParam String type){
        Code code = new Code(text, type);
        text = text.replaceAll("\n", "<br>");
        text = text.replaceAll(" ", "&nbsp;");
        System.out.println(code.toString());
        return code.toString();
    }
}
