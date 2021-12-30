package com.mewhz.paste.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Heap {

    @ResponseBody
    @RequestMapping("/head")
    public JSONObject head(){
        JSONObject json = JSONUtil.createObj();

        json.append("name", "小明").append("age", "20");

        return json;
    }
}
