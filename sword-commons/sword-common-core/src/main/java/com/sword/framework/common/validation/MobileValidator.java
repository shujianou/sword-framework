package com.sword.framework.common.validation;

import cn.hutool.core.util.StrUtil;
import com.sword.framework.common.core.utils.BizStrRuleUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public void initialize(Mobile annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果手机号为空，默认不校验，即校验通过
        if (StrUtil.isEmpty(value)) {
            return true;
        }
        // 校验手机
        return BizStrRuleUtil.matchPhone(value);
    }

}
