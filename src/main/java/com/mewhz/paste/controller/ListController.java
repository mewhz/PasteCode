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

    @RequestMapping(value = "/list")
    public String list(@RequestParam(value = "id", required = false) String id ){

        if (id == null || "".equals(id)) {
            return "<h1>未输入标识码</h1>";
        }

        String htmlBegin = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>贴代码</title>\n" +
                "    <link href=\"css/prism.css\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "<body>";
        String summaryBegin = "<details>\n" +
                "    <summary>";
        String summaryEnd = "</summary>\n" +
                "    <pre>\n" +
                "      <code class=\"language-";
        String detailsEnd = "      </code>\n" +
                "    </pre>\n" +
                "  </details>\n";
        String htmlEnd = "  <script src=\"js/prism.js\"></script>\n" +
                "  <script src=\"js/prism-c.min.js\"></script>\n" +
                "  <script src=\"js/prism-cpp.min.js\"></script>\n" +
                "  <script src=\"js/prism-java.min.js\"></script>\n" +
                "</body>\n" +
                "</html>";
        StringBuilder html = new StringBuilder(htmlBegin);

        CodeSQL codeSQL = new CodeSQL();

        List<Entity> list = codeSQL.findIdentifyingCode(id);

        if (list.size() == 0) {
            return "<h1>未找到该识别码</h1>";
        }

        for (Entity e : list){
            html.append(summaryBegin);
            html.append(e.get("date"));
            html.append(" ");
            html.append(e.get("time_id"));
            html.append(" ");
            html.append(e.get("remark"));
            html.append(summaryEnd);
            html.append(e.get("type"));
            html.append("\">");

            String text = (String) e.get("text");

            text = text.replaceAll("<", "&lt;");
            text = text.replaceAll(">", "&gt;");
            text = text.replaceAll("\n", "<br>");
            text = text.replaceAll(" ", "&nbsp;");

            html.append(text);
            html.append(detailsEnd);
        }
        html.append(htmlEnd);

        return html.toString();
    }


    @RequestMapping("/lists")
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
