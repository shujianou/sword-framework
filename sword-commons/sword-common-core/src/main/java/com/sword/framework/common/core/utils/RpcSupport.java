package com.sword.framework.common.core.utils;

import com.sword.framework.common.core.bean.R;
import com.sword.framework.common.core.enums.ResultEnum;
import com.sword.framework.common.core.exceptions.BizException;

/**
 * Rpc支持类
 * Created by shujian.ou 2023/5/24 11:01
 */
public class RpcSupport {

    public static <T> T checkAndGetData(R<T> r) {
        if (r == null) {
            throw new BizException(ResultEnum.SYSTEM_BUSY);
        }
        if (!r.isSuccess()) {
            throw new BizException(r.getCode(), r.getMessage());
        }
        return r.getData();
    }
}
