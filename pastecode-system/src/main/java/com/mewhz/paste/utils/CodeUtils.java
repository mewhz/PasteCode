package com.mewhz.paste.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mewhz
 */
public class CodeUtils {

    public static String getCodeFileSuffix(String codeType)  {

        Map<String, String> map = new HashMap<>();

        map.put("cpp", ".cpp");
        map.put("java", ".java");
        map.put("python", ".py");

        return map.get(codeType);
    }
}
