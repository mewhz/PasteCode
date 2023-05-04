package com.mewhz.paste.main;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RuntimeUtil;

import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String javaCode = "public class Main {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello World\");\n" +
                "   }\n" +
                "}\n";

        String pythonCode = "print(\"Hello World\")";

        String cppCode = "#include <bits/stdc++.h>\n" +
                "using namespace std;\n" +
                "int main() {\n" +
                "    cout << \"Hello World\" << endl;\n" +
                "    return 0;\n" +
                "}";

        mkdirCode(javaCode, "java");
        mkdirCode(pythonCode, "python");
        mkdirCode(cppCode, "cpp");

        runCode("java");
        runCode("python");
        runCode("cpp");
    }
    
    public static void mkdirCode(String code, String type) throws IOException {

        String fileName = "e:/runCode/code/";

        if ("java".equals(type)) {
            fileName += "Main.java";
        }
        else if ("python".equals(type)) {
            fileName += "main.py";
        }
        else if ("cpp".equals(type)) {
            fileName += "main.cpp";
        }

        FileWriter fileWriter = new FileWriter(fileName);

        fileWriter.write(code);

        fileWriter.close();

    }

    public static void runCode(String type) {
        String fileName = "e:/runCode/" + type + ".bat";

        RuntimeUtil.execForStr(CharsetUtil.CHARSET_UTF_8, fileName);

        fileName = "e:/runCode/code/" + type + "-output.txt";

        FileReader fileReader = new FileReader(fileName);

        String result = fileReader.readString();

        System.out.println(type + ":" + result);

    }
}
