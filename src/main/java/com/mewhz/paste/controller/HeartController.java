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
public class HeartController {

    @RequestMapping("heart")
    @ResponseBody
    public String heart(){
        return "success";
    }
}