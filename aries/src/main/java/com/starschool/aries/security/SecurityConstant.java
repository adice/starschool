package com.starschool.aries.security;

/**
 * @description: TODO
 * @author: adi
 * @since 2023/5/6 8:38
 */
public class SecurityConstant {
    public static boolean IS_SECURITY_OPEN = false;
    public static String[] WHITE_PATHS = {"/taurus/teacher/**", "/aries/teacher/login", "/teacher/login"};
}
