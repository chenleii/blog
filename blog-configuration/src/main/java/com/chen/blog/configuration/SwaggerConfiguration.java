package com.chen.blog.configuration;

import com.chen.blog.configuration.properties.ApiProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.inject.Inject;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 文档：<a href="https://swagger.io/docs/specification/about/">链接</a>
 *
 * @author cl
 * @version 1.0
 * @since 2020/11/23 21:03
 */
@EnableConfigurationProperties(ApiProperties.class)
@ConditionalOnProperty(prefix = "api.doc", name = "enabled", havingValue = "true")
@ConditionalOnWebApplication
@Configuration
@Import(SpringDocConfiguration.class)
public class SwaggerConfiguration {

    @Inject
    private ApiProperties apiProperties;


    @Bean
    public OpenAPI blogOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("人人博客接口文档")
                        .description("人人博客接口文档")
                        .version("1.0.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://github.com/chenleii/blog")
                        )
                        .contact(new Contact()
                                .name("chenlei")
                                .url("https://github.com/chenleii/blog")
                                .email("chenleinxd@gamil.com")
                        )
                );

        if (apiProperties.getJwt().isEnabled()) {
            openAPI
                    .components(new Components()
                            .addSecuritySchemes("bearer-jwt",
                                    new SecurityScheme()
                                            .scheme("bearer")
                                            .name("Bearer JWT Authentication")
                                            .bearerFormat("JWT")
                                            .type(SecurityScheme.Type.HTTP)
                                            .in(SecurityScheme.In.HEADER))

                    )
                    .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
        }

        return openAPI;
    }
}
