package com.sword.framework.common.core.exceptions;

import com.sword.framework.common.core.bean.IResultEnum;
import com.sword.framework.common.core.enums.ResultEnum;
import com.sword.framework.common.core.utils.MessageUtils;

/**
 * @author shujian.ou
 * @since 2022/12/13 14:35
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code = ResultEnum.DEFAULT_ERROR_CODE;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public BizException() {
    }

    public BizException(String message) {
        this.message = message;
    }

    public BizException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public BizException(IResultEnum resultEnum) {
        this.message = MessageUtils.message(resultEnum.getMsgKey());
        this.code = resultEnum.getCode();
    }

    public BizException(IResultEnum resultEnum, Object... args) {
        this.message = MessageUtils.message(resultEnum.getMsgKey(), args);
        this.code = resultEnum.getCode();
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public BizException setMessage(String message) {
        this.message = message;
        return this;
    }

    public BizException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }

    public static void throwBy(String message) {
        throw new BizException(message);
    }

    public static void throwBy(Integer code, String message) {
        throw new BizException(code, message);
    }

    public static void throwBy(IResultEnum resultEnum) {
        throw new BizException(resultEnum);
    }

    public static void throwBy(IResultEnum resultEnum, Object... args) {
        throw new BizException(resultEnum, args);
    }

}
