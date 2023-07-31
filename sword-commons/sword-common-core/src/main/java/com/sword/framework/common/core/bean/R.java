package com.sword.framework.common.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sword.framework.common.core.enums.ResultEnum;
import com.sword.framework.common.core.utils.MessageUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口返回统一Bean
 *
 * @author Charlie, Irany 2018/5/13 23:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class R<T> extends AbstractResult<T> {

    private static final int SUCCESS = 200;
    private static final int FAILED = ResultEnum.SYSTEM_ERROR.getCode();

    private int code = SUCCESS;

    private String message;
    @JsonInclude
    private T data;


    public R() {
        this.message = MessageUtils.message(ResultEnum.OPERATION_SUCCESS.getMsgKey());
    }

    public R(T data) {
        this.data = data;
    }

    private R(int code) {
        this();
        this.code = code;
    }

    private R(int code, String message) {
        this(code);
        this.message = message;
    }

    private R(Integer code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public R(IResultEnum resultEnum, T data) {
        this(resultEnum.getCode(), MessageUtils.message(resultEnum.getMsgKey()));
        this.data = data;
    }

    public R(IResultEnum resultEnum) {
        this(resultEnum.getCode(), MessageUtils.message(resultEnum.getMsgKey()));
        this.data = null;
    }


    public static <T> R<T> ok() {
        return new R<>();
    }

    public static <T> R<T> fail() {
        return new R<>(FAILED, MessageUtils.message(ResultEnum.SYSTEM_BUSY));
    }

    public static <T> R<T> fail(String msg) {
        return new R<>(R.FAILED, msg);
    }

    public static <T> R<T> custom(Integer code, String msg) {
        return new R<>(code, msg);
    }


    public static <T> R<T> custom(IResultEnum resultEnum) {
        return new R<>(resultEnum.getCode(), MessageUtils.message(resultEnum.getMsgKey()));
    }

    public static <T> R<T> custom(IResultEnum resultEnum, T data) {
        return new R<>(resultEnum.getCode(), MessageUtils.message(resultEnum.getMsgKey()), data);
    }

    public static <T> R<T> ok(T data) {
        return new R<>(SUCCESS, MessageUtils.message(ResultEnum.OPERATION_SUCCESS), data);
    }

    public static <T> R<T> ok(String msg, T data) {
        return new R<>(SUCCESS, msg, data);
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }
}
