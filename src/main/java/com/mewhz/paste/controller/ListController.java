package com.mewhz.paste.controller;

import cn.hutool.db.Entity;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mewhz.paste.utils.CodeSQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author mewhz
 */

@RestController
public  class ListController {
    
    @RequestMapping("/list")
    public ModelAndView lists(@RequestParam(value = "id", required = false) String id ){

        ModelAndView mav = new ModelAndView("/list.html");

        return mav;

    }

    @RequestMapping("/selectList")
    @CrossOrigin("http://localhost:63343")
    public JSONObject selectList(@RequestParam(value = "id", required = false) String id){
        JSONObject json = JSONUtil.createObj();
        CodeSQL codeSQL = new CodeSQL();

        List<Entity> list = codeSQL.findIdentifyingCode(id);
        if (list.size() == 0){
            json.append("info", "zero");
            return json;
        }
        for (Entity e : list){
            json.append("date", e.get("date"));
            json.append("time_id", e.get("time_id"));
            json.append("remark", e.get("remark"));
            json.append("type", e.get("type"));
            json.append("text", e.get("text"));
        }
        System.out.println(json);
        return json;
    }

}
