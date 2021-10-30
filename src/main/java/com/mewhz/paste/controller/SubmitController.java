package com.mewhz.paste.controller;

import com.mewhz.paste.model.Code;
import com.mewhz.paste.utils.CodeSQL;
import com.mewhz.paste.utils.IPUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class SubmitController {

    @RequestMapping("/submit")
//    @ResponseBody
    @CrossOrigin
    public String code(@RequestParam String text, @RequestParam String type, HttpServletRequest request){
        Date date = new Date();
        String ip = IPUtils.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
        Code code = new Code(text, type, date, ip, userAgent);
        System.out.println(code);
        CodeSQL codeSql = new CodeSQL();
        codeSql.insertCode(code);
        return "redirect:/code?id=" + code.getDate().getTime();
    }
}
