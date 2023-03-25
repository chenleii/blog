package com.chen.blog.configuration;

import com.chen.blog.configuration.properties.ApiProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.inject.Inject;

/**
 * 文档：https://swagger.io/docs/specification/about/
 *
 * @author cl
 * @version 1.0
 * @since 2020/11/23 21:03
 */
@EnableConfigurationProperties(ApiProperties.class)
@ConditionalOnProperty(prefix = "api.doc", name = "enabled", havingValue = "true")
@Configuration
public class SwaggerConfiguration {

    @Inject
    private ApiProperties apiProperties;

    @Bean
    public GroupedOpenApi userApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.chen.blog.interfaces.http"};
        return GroupedOpenApi.builder()
                .group("接口文档")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch)
                .build();
    }


    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(
                        new Info()
                                .title("接口文档")
                                .description("接口文档")
                                .version("1.0.0")
                                .termsOfService("https://example.com/terms/")
                                .license(
                                        new License()
                                                .name("Apache 2.0")
                                                .identifier("Apache-2.0")
                                )
                                .contact(
                                        new Contact()
                                                .name("API Support")
                                                .url("https://www.example.com/support")
                                                .email("chenleinxd@gamil.com")
                                )
                );

        if (apiProperties.getJwt().isEnabled()) {
            openAPI
                    .components(
                            new Components()
                                    .addSecuritySchemes("bearer-jwt",
                                            new SecurityScheme()
                                                    .scheme("bearer")
                                                    .name("Bearer JWT Authentication")
                                                    .bearerFormat("JWT")
                                                    .type(SecurityScheme.Type.HTTP)
                                                    .in(SecurityScheme.In.HEADER))

                    )
                    .addSecurityItem(
                            new SecurityRequirement().addList("bearer-jwt")
                    );
        }

        return openAPI;
    }
}
