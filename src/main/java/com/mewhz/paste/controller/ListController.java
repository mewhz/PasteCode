package com.mewhz.paste.controller;

import cn.hutool.db.Entity;
import com.mewhz.paste.utils.CodeSQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author mewhz
 */
@Controller
public class ListController {

    @ResponseBody
    @RequestMapping("/list")
    public String list(@RequestParam String id){
        String htmlBegin = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
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

        for (Entity e : list){
            html.append(summaryBegin);
            html.append(e.get("date"));
            html.append(" ");
            html.append(e.get("time_id"));
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
}
