package com.sword.framework.common.core.utils;

import cn.hutool.core.util.ReUtil;

/**
 * @author shujian.ou
 * @since 2022/12/30 9:39
 */
public class BizStrRuleUtil {

    /**
     * 手机号正则
     */
    public static final String PHONE_REG = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    /**
     * 手机号是否符合规则
     */
    public static boolean matchPhone(String phone) {
        return ReUtil.isMatch(PHONE_REG, phone);
    }

}
