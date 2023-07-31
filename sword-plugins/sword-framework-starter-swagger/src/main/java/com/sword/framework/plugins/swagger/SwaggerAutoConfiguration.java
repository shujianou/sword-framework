package com.sword.framework.plugins.swagger;

import com.sword.framework.plugins.swagger.config.SwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shujian.ou
 * @since 2022/12/13 12:30
 */
@RequiredArgsConstructor
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
public class SwaggerAutoConfiguration {

    private final SwaggerProperties swaggerProperties;

    private final ServiceInstance serviceInstance;

    @Bean
    public OpenAPI springOpenAPI() {
        OpenAPI openAPI = new OpenAPI().info(new Info().title(swaggerProperties.getTitle()));
        // oauth2.0 password
        /*openAPI.addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));
        openAPI.schemaRequirement(HttpHeaders.AUTHORIZATION, this.securityScheme());*/
        // servers 提供调用的接口地址前缀
        List<Server> serverList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(swaggerProperties.getServices())) {
            String path = swaggerProperties.getServices().get(serviceInstance.getServiceId());
            serverList.add(new Server().url(swaggerProperties.getGateway() + "/" + path));
            openAPI.servers(serverList);
        }
        return openAPI;
    }

}
