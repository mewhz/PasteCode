package com.mewhz.paste.controller;

import com.mewhz.paste.model.Code;
import com.mewhz.paste.model.IdentifyingCode;
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
    @CrossOrigin
    public String code(@RequestParam String text, @RequestParam String type, @RequestParam String identifying ,@RequestParam String remark,HttpServletRequest request){
        Date date = new Date();
        String ip = IPUtils.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
        System.out.println(remark);
        Code code = new Code(text, type, date, ip, userAgent, remark);
        IdentifyingCode identifyingCode = new IdentifyingCode(identifying, date.getTime()+"");
        CodeSQL codeSql = new CodeSQL();
        codeSql.insertCode(code);
        if (!"".equals(identifying)){
            codeSql.insertIdentifyingCode(identifyingCode);
        }
        return "redirect:/code?id=" + code.getDate().getTime();
    }
}
