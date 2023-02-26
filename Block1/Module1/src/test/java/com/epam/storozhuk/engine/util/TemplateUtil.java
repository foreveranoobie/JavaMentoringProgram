package com.epam.storozhuk.engine.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class TemplateUtil {

    public static Map<String, String> generateMapOfValues(String[] keys, String[] values) {
        Map<String, String> variables = new LinkedHashMap<>();
        for (int i = 0; i < keys.length || i < values.length; i++) {
            variables.put(keys[i], values[i]);
        }
        return variables;
    }
}
