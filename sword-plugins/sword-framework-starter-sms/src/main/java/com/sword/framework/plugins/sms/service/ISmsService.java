package com.sword.framework.plugins.sms.service;

import java.util.Map;

/**
 * Created by shujian.ou 2022-12-22 13:28
 */
public interface ISmsService {


    /**
     * 发送短信验证码
     *
     * @param code   短信验证码
     * @param phones 手机号
     */
    String sendCode(final String code, final String... phones);


    /**
     * 通过短信模板发送短信验证码
     *
     * @param templateKey 模板KEY
     * @param code        短信验证码
     * @param phones      手机号
     */
    String sendCodeByKey(final String templateKey, final String code, final String... phones);


    /**
     * 通过模板发送短信
     *
     * @param templateKey 模板KEY
     * @param params      参数
     * @param phones      手机号
     */
    void sendParamByKey(final String templateKey, final Map<String, String> params, final String... phones);


}
