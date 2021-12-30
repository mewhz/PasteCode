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

import java.util.List;

/**
 * @author mewhz
 */
@RestController
public class CodeController {

    @RequestMapping("/code")
    public String code(@RequestParam String id) {
        String htmlFront = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>";
        String htmlTitle ="</title>\n" +
                "    <link href=\"css/prism.css\" rel=\"stylesheet\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/bootstrap.min.css\"/>\n" +
                "    <link href=\"https://cdn.bootcdn.net/ajax/libs/toastr.js/latest/css/toastr.min.css\" rel=\"stylesheet\">"+
                "</head>\n" +
                "<body>\n" +
                "    <pre>\n" +
                "      <code id=\"code\" class=\"language-";
        String type = null;
        String text = null;
        String remark = null;
        String htmlLast = "      </code>\n" +
                "    </pre>\n" +
                "<button id=\"button\" class=\"btn btn-success\" class=\"btn btn-success\" onclick=\"copy()\" data-clipboard-target=\"#code\" data-clipboard-action=\"copy\">一键复制</button>"+
                "    <script src=\"js/prism.js\"></script>\n"+
                "<script src=\"js/jquery-3.6.0.min.js\"></script>\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/clipboard@2.0.8/dist/clipboard.min.js\"></script>\n"+
                        "<script src=\"js/code.js\"></script>\n" +
                        "<script src=\"https://cdn.bootcdn.net/ajax/libs/toastr.js/latest/js/toastr.min.js\"></script>";

        String htmlEnd = "</body>\n" +
                "</html>";
        StringBuilder html = new StringBuilder(htmlFront);

        String cHtml = "<script src=\"js/prism-c.min.js\"></script>\n" +
                        "<script src=\"js/prism-cpp.min.js\"></script>\n";
        String javaHtml = "<script src=\"js/prism-java.min.js\"></script>\n";
        String pythonHtml = "<script src=\"js/prism-python.min.js\"></script>\n";


        CodeSQL codesql = new CodeSQL();
        List<Entity> list = codesql.findCode(id);

        type = (String) list.get(0).get("type");
        text = (String) list.get(0).get("text");
        remark = (String) list.get(0).get("remark");
        if ("".equals(remark)){
            remark = "贴代码";
        }

        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll("\n", "<br>");
        text = text.replaceAll(" ", "&nbsp;");

        html.append(remark).append(htmlTitle).append(type).append("\">").append(text).append(htmlLast);
        if ("cpp".equals(type)){
            html.append(cHtml);
        }
        else    if ("java".equals(type)){
            html.append(javaHtml);
        }
        else    if ("python".equals(type)){
            html.append(pythonHtml);
        }
        html.append(htmlEnd);

        return html.toString();
    }


    @RequestMapping("/codes")
    public JSONObject codes(@RequestParam String id){

        JSONObject json = JSONUtil.createObj();
        String type = null;
        String text = null;
        String remark = null;

        CodeSQL codesql = new CodeSQL();
        List<Entity> list = codesql.findCode(id);

        type = (String) list.get(0).get("type");
        text = (String) list.get(0).get("text");
        remark = (String) list.get(0).get("remark");

//        text = text.replaceAll("<", "&lt;");
//        text = text.replaceAll(">", "&gt;");
//        text = text.replaceAll("\n", "<br>");
//        text = text.replaceAll(" ", "&nbsp;");


        json.append("text", text);
        json.append("type", type);
        json.append("remark", remark);


        return json;
    }
}
