package com.mewhz.paste.model;

public class CodeHtml {
    private String name;
    private String frontHtml;
    private String type;
    private String codeStr;
    private String backHtml;

    public CodeHtml(){

    }

    public CodeHtml(String name, String codeStr, String type) {
        this.name = name;
        this.codeStr = codeStr;
        frontHtml = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "    <link href=\"css/prism.css\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "<body>\n" +
                "    <pre>\n" +
                "      <code class=\"language-";
        this.type = type;
        backHtml = "</code>\n" +
                "    </pre>\n" +
                "    <script src=\"js/prism.js\"></script>\n" +
                "    <script src=\"//cdn.jsdelivr.net/npm/prismjs@1/components/prism-c.min.js\"></script>\n" +
                "    <script src=\"//cdn.jsdelivr.net/npm/prismjs@1/components/prism-cpp.min.js\"></script>\n" +
                "    <script src=\"//cdn.jsdelivr.net/npm/prismjs@1/components/prism-java.min.js\"></script>\n" +
                "</body>\n" +
                "</html>";

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrontHtml() {
        return frontHtml;
    }

    public void setFrontHtml(String frontHtml) {
        this.frontHtml = frontHtml;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCodeStr() {
        return codeStr;
    }

    public void setCodeStr(String codeStr) {
        this.codeStr = codeStr;
    }

    public String getBackHtml() {
        return backHtml;
    }

    public void setBackHtml(String backHtml) {
        this.backHtml = backHtml;
    }

    @Override
    public String toString() {
        return "CodeHtml{" +
                "name='" + name + '\'' +
                ", frontHtml='" + frontHtml + '\'' +
                ", type='" + type + '\'' +
                ", codeStr='" + codeStr + '\'' +
                ", backHtml='" + backHtml + '\'' +
                '}';
    }
}
