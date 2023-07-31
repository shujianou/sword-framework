package com.sword.framework.common.core.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shujian.ou
 * @since 2023/1/4 15:27
 */

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RpcImPaasResult<T> extends R<T> {

    private String message;

    public RpcImPaasResult() {
        super();
    }

    public RpcImPaasResult(T data) {
        super(data);
    }

    public RpcImPaasResult(IResultEnum resultEnum, T data) {
        super(resultEnum, data);
    }

    public RpcImPaasResult(IResultEnum resultEnum) {
        super(resultEnum);
    }


}
