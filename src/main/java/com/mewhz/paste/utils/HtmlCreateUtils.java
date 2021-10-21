package com.mewhz.paste.utils;

import com.mewhz.paste.model.CodeHtml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class HtmlCreateUtils {

    private CodeHtml codeHtml;
    private OutputStream out;

    public HtmlCreateUtils() {

    }
    public HtmlCreateUtils(CodeHtml codeHtml){
        this.codeHtml = codeHtml;
    }

    public void createHtml() {
        String text = codeHtml.getCodeStr();
        File file = new File("src/main/resources/static/code/"+ codeHtml.getName() + ".html");
        text = text.replaceAll("\n", "<br>");
        text = text.replaceAll(" ", "&nbsp;");
        StringBuilder htmlString = new StringBuilder(codeHtml.getFrontHtml());
        htmlString.append(codeHtml.getType()).append("\"><br>").append(text).append(codeHtml.getBackHtml());
        try {
            out = new FileOutputStream(file);
            byte[] bytes = htmlString.toString().getBytes(StandardCharsets.UTF_8);
            for (byte b : bytes) {
                out.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
