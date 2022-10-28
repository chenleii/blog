package com.chen.blog.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author cl
 * @since 2021/3/2 21:33.
 */
@EnableRetry
@EnableAsync
@SpringBootApplication(
        scanBasePackages = {
                "com.chen.blog.core",
                "com.chen.blog.infrastructure",
                "com.chen.blog.configuration",
                "com.chen.blog.interfaces.http",
                "com.chen.blog.interfaces.timedtask",
        }
)
@EnableElasticsearchRepositories(basePackages = "com.chen.blog.infrastructure.persistence.repository.elasticsearch")
@EnableMongoRepositories(basePackages = "com.chen.blog.infrastructure.persistence.repository.mongodb")
public class TestApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TestApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}