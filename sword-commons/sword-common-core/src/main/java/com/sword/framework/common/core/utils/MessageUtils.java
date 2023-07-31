package com.sword.framework.common.core.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.sword.framework.common.core.bean.IResultEnum;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 获取i18n资源文件
 *
 * @author shujian.ou
 * @since 2022/12/13 11:23
 */
public class MessageUtils {
    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
    public static String message(String code, Object... args) {
        MessageSource messageSource = SpringUtil.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public static String message(IResultEnum resultEnum, Object... args) {
        MessageSource messageSource = SpringUtil.getBean(MessageSource.class);
        return messageSource.getMessage(resultEnum.getMsgKey(), args, LocaleContextHolder.getLocale());
    }

    public static String message(IResultEnum resultEnum, Locale locale, Object... args) {
        MessageSource messageSource = SpringUtil.getBean(MessageSource.class);
        return messageSource.getMessage(resultEnum.getMsgKey(), args, locale);
    }
}
