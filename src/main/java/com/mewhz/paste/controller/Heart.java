package com.mewhz.paste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Heart {

    @ResponseBody
    @RequestMapping("/heart")
    public String heart(){
        return "success";
    }
}
