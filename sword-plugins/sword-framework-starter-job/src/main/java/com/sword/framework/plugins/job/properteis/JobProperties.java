package com.sword.framework.plugins.job.properteis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author shujian.ou
 * @since 2023/1/5 12:53
 */

@Getter
@Setter
@ConfigurationProperties(prefix = "im.job")
public class JobProperties {

    private Boolean enable;

    private String adminAddresses;

    private String accessToken;

    private String executorAppname;

    private String executorAddress;

    private String executorIp;

    private int executorPort;

    private String executorLogPath;

    private int executorLogRetentionDays;
}
