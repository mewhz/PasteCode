package com.mewhz.paste.controller;


import cn.hutool.db.Entity;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mewhz.paste.utils.CodeSQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author mewhz
 */
@RestController
public class CodeController {

    @RequestMapping("/code")
    public ModelAndView code(@RequestParam String id){
        ModelAndView mav = new ModelAndView("/code.html?id=" + id);
        return mav;
    }

    @RequestMapping("/selectCode")
    public JSONObject selectCode(@RequestParam String id){

        JSONObject json = JSONUtil.createObj();
        String type = null;
        String text = null;
        String remark = null;

        CodeSQL codesql = new CodeSQL();
        List<Entity> list = codesql.findCode(id);

        type = (String) list.get(0).get("type");
        text = (String) list.get(0).get("text");
        remark = (String) list.get(0).get("remark");

        json.append("text", text);
        json.append("type", type);
        json.append("remark", remark);

        return json;
    }
}
