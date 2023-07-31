package com.sword.framework.plugins.job;

import cn.hutool.core.util.StrUtil;
import com.sword.framework.plugins.job.properteis.JobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author shujian.ou
 * @since 2022/12/28 10:23
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({JobProperties.class})
@Slf4j
public class JobAutoConfiguration {


    @Bean
    @ConditionalOnProperty(prefix = "im.job", name = "enable", havingValue = "true")
    @ConditionalOnMissingBean(XxlJobSpringExecutor.class)
    public XxlJobSpringExecutor xxlJobExecutor(JobProperties properties) {
        log.info(">>>>>>>>>>> 任务调度执行器启动成功.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(properties.getAdminAddresses());
        xxlJobSpringExecutor.setAppname(properties.getExecutorAppname());
        xxlJobSpringExecutor.setAddress(properties.getExecutorAddress());
        String ip = properties.getExecutorIp();
        xxlJobSpringExecutor.setIp(!StrUtil.isEmpty(ip) ? ip : getIpAddress());
        xxlJobSpringExecutor.setPort(properties.getExecutorPort());
        xxlJobSpringExecutor.setAccessToken(properties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(properties.getExecutorLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(properties.getExecutorLogRetentionDays());

        return xxlJobSpringExecutor;
    }

    private String getIpAddress() {
        InetUtilsProperties properties = new InetUtilsProperties();
        properties.setIgnoredInterfaces(Arrays.asList("docker0", "veth.*", ".*VMware.*", ".*VirtualBox.*"));
        InetUtils inetUtils = new InetUtils(properties);
        String ip = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
        log.info("取到本机IP: {}", ip);
        return ip;
    }
    /**
     * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
     *
     *      1、引入依赖：
     *          <dependency>
     *             <groupId>org.springframework.cloud</groupId>
     *             <artifactId>spring-cloud-commons</artifactId>
     *             <version>${version}</version>
     *         </dependency>
     *
     *      2、配置文件，或者容器启动变量
     *          spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
     *
     *      3、获取IP
     *          String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
     */

}
