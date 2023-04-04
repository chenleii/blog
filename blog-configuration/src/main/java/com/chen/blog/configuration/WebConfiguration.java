package com.chen.blog.configuration;

import com.chen.blog.core.sharedkernel.serializer.json.JacksonJsonSerializer;
import com.chen.blog.infrastructure.tracer.OpenTracerHttpFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.time.Instant;


/**
 * @author cl
 * @version 1.0
 * @since 2020/11/5
 */
//@EnableWebMvc
@Configuration
public class WebConfiguration implements WebMvcConfigurer {


    @Bean
    public FilterRegistrationBean<OpenTracerHttpFilter> httpTraceFilterFilterRegistrationBean() {
        FilterRegistrationBean<OpenTracerHttpFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new OpenTracerHttpFilter());
        filterFilterRegistrationBean.addUrlPatterns("/*");
        filterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterFilterRegistrationBean;
    }

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 为了不影响其他接口功能，前端页面以/blog开头。
        registry.addViewController("/").setViewName("redirect:/blog");
        registry.addViewController("/blog/**").setViewName("forward:/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 首页
        registry.addResourceHandler("/**").addResourceLocations("classpath:/ui/");

        // swagger相关
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-resources/**").addResourceLocations("classpath:/META-INF/swagger-resources/webjars/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, Instant>() {
            @Override
            public Instant convert(String s) {
                if (StringUtils.isBlank(s)) {
                    return null;
                }
                return Instant.ofEpochMilli(Long.parseLong(s));
            }
        });
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return (builder) -> {
            // 统一
            builder.postConfigurer(JacksonJsonSerializer::configure);
            builder.configure(JacksonJsonSerializer.getObjectMapper());

        };
    }
}
