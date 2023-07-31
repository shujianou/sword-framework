package com.sword.framework.common.core.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author shujian.ou
 * @since 2022/12/27 10:51
 */
public class SecurityUtil {

    private final static PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 密码是否与加密密码匹配
     *
     * @param pwd       未加密密码
     * @param encodePwd 已加密密码
     */
    public static boolean isMatchPwd(String pwd, String encodePwd) {
        return ENCODER.matches(pwd, encodePwd);
    }

    /**
     * 加密密码
     * @param pwd 未加密的密码
     */
    public static String encode(String pwd) {
        return ENCODER.encode(pwd);
    }
}
