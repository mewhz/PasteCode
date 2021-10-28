package com.mewhz.paste.controller;


import cn.hutool.db.Entity;
import com.mewhz.paste.utils.CodeSQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CodeController {

    @RequestMapping("/code")
    @ResponseBody
    public String code(@RequestParam String id){
        String htmlFront = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "    <link href=\"css/prism.css\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "<body>\n" +
                "    <pre>\n" +
                "      <code class=\"language-";
        String type = null;
        String text = null;
        String htmlLast = "      </code>\n" +
                "    </pre>\n" +
                "    <script src=\"js/prism.js\"></script>\n" +
                "    <script src=\"js/prism-c.min.js\"></script>\n" +
                "    <script src=\"js/prism-cpp.min.js\"></script>\n" +
                "    <script src=\"js/prism-java.min.js\"></script>\n" +
                "</body>\n" +
                "</html>";
        StringBuilder html = new StringBuilder(htmlFront);

        CodeSQL codesql = new CodeSQL();
        List<Entity> list = codesql.findCode(id);

        type = (String) list.get(0).get("type");
        text = (String) list.get(0).get("text");

        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll("\n", "<br>");
        text = text.replaceAll(" ", "&nbsp;");

        html.append(type).append("\">").append(text).append(htmlLast);

        return html.toString();
    }
}
