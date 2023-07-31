package com.sword.framework.plugins.oss.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author shujian.ou
 * @since 2022/12/28 10:45
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(prefix = "im.oss")
public class OssProperties {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;


    @Value("${im.oss.sts.endpoint}")
    private String stsEndpoint;

    @Value("${im.oss.sts.accessKeyId}")
    private String stsAccessKeyId;

    @Value("${im.oss.sts.accessKeySecret}")
    private String stsAccessKeySecret;

    @Value("${im.oss.sts.roleArn}")
    private String stsRoleArn;

    @Value("${im.oss.sts.roleSessionName}")
    private String stsRoleSessionName;

    @Value("${im.oss.sts.regionId}")
    private String stsRegionId;

}
