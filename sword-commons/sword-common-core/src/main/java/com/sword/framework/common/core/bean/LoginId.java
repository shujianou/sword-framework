package com.sword.framework.common.core.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 登录ID
 * @author shujian.ou
 * @since 2023-01-15 1:14
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginId implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 客户端ID
     */
    private String clientId;

}
