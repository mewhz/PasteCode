package com.mewhz.paste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author mewhz
 */

@Controller
public class LoginController {

    @ResponseBody
    @RequestMapping("/login")
    public String login(@RequestParam String user, @RequestParam String pass){
        System.out.println(user + " " + pass);
        if (!"admin".equals(user) || !"admin".equals(pass)){
            return "ERROR";
        }
        return "success";
    }
}
