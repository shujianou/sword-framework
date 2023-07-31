package com.sword.framework.common.core.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author shujian.ou
 * @since 2022/12/14 14:43
 */
public abstract class AbstractResult<T> implements IResult<T> {

    @Override
    @JsonIgnore
    public boolean isSuccess() {
        return getCode() == 200;
    }
}
