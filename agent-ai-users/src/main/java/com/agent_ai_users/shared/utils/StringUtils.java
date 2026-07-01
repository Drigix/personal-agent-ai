package com.agent_ai_users.shared.utils;

public class StringUtils {

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty() || string.trim().isEmpty();
    }
}
