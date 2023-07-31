package com.sword.framework.plugins.mq.annotation;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启swagger文档
 *
 * @author shujian.ou
 * @since 2022/12/13 16:46
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({RocketMQAutoConfiguration.class})
public @interface EnableRocketMQ {

}
