package com.sword.framework.plugins.swagger.annotation;

import com.sword.framework.plugins.swagger.SwaggerAutoConfiguration;
import com.sword.framework.plugins.swagger.config.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({SwaggerAutoConfiguration.class})
public @interface EnableSwaggerDoc {

}
