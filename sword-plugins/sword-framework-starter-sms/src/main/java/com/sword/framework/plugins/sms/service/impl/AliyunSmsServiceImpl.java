package com.sword.framework.plugins.sms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.sword.framework.common.core.enums.ResultEnum;
import com.sword.framework.common.core.exceptions.BizException;
import com.sword.framework.plugins.sms.service.ISmsService;
import io.springboot.sms.core.SmsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by shujian.ou 2022-12-22 13:30
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AliyunSmsServiceImpl implements ISmsService {

    private final SmsClient smsClient;

    /**
     * 发送短信验证码
     *
     * @param code   短信验证码
     * @param phones 手机号
     */
    @Override
    public String sendCode(final String code, final String... phones) {
        return smsClient.sendCode(code, phones);
    }


    /**
     * 通过短信模板发送短信验证码
     *
     * @param templateKey 模板KEY
     * @param code        短信验证码
     * @param phones      手机号
     */
    @Override
    public String sendCodeByKey(final String templateKey, final String code, final String... phones) {
        if (StrUtil.isEmpty(templateKey)) {
            log.error("发送短信失败, templateKey不能为空");
            throw new BizException(ResultEnum.SYSTEM_ERROR);
        }
        if (StrUtil.isEmpty(code)) {
            log.error("发送短信失败, code不能为空");
            throw new BizException(ResultEnum.SYSTEM_ERROR);
        }
        if (phones == null) {
            log.error("发送短信失败,phones不能为空");
            throw new BizException(ResultEnum.SYSTEM_ERROR);
        }

        return smsClient.sendCodeByKey(templateKey, code, phones);
    }


    /**
     * 通过模板发送短信
     *
     * @param templateKey 模板KEY
     * @param params      参数
     * @param phones      手机号
     */
    @Override
    public void sendParamByKey(final String templateKey, final Map<String, String> params, final String... phones) {
        if (StrUtil.isEmpty(templateKey)) {
            log.error("发送短信失败, templateKey不能为空");
            throw new BizException(ResultEnum.SYSTEM_ERROR);
        }
        if (CollUtil.isEmpty(params)) {
            log.error("发送短信失败, params不能为空");
            throw new BizException(ResultEnum.SYSTEM_ERROR);
        }
        if (phones == null) {
            log.error("发送短信失败,phones不能为空");
            throw new BizException(ResultEnum.SYSTEM_ERROR);
        }
        smsClient.sendParamByKey(templateKey, params, phones);
    }
}
