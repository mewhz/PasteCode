package com.mewhz.paste.controller;

import cn.hutool.core.net.NetUtil;
import com.mewhz.paste.utils.IPUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HeartController {

    @RequestMapping("heart")
    @ResponseBody
    @CrossOrigin
    public String heart(HttpServletRequest request){
        String userAgent = request.getHeader("user-agent");
        System.out.println(userAgent);
        String ip = IPUtils.getIpAddr(request);
        System.out.println(ip);
        return "success";
    }
}