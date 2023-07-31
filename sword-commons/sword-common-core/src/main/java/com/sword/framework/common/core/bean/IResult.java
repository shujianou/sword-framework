package com.sword.framework.common.core.bean;


/**
 * @author shujian.ou
 * @since 2022/12/13 16:20
 */
public interface IResult<T> {

    int getCode();

    String getMessage();

    T getData();

    /**
     * 是否请求成功
     */
    boolean isSuccess();
}
