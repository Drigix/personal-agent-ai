package com.demo.agent_ai.utils;

public class StringUtils {

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty() || string.trim().isEmpty();
    }
}
