package com.sword.framework.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author shujian.ou
 * @since 2022/12/19 17:34
 */
@AllArgsConstructor
@Getter
public enum SexEnum {
    FEMALE(0, "女"),
    MALE(1, "男");

    private final int code;

    private final String descp;

}
