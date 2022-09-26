package com.chen.blog.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot应用的入口类
 *
 * @author cl
 */
@EnableScheduling
@EnableRetry
@EnableAsync
@SpringBootApplication(
        scanBasePackages = {"com.chen.blog"}
)
@EnableElasticsearchRepositories(basePackages = "com.chen.blog.infrastructure.persistence.repository.elasticsearch")
@EnableMongoRepositories(basePackages = "com.chen.blog.infrastructure.persistence.repository.mongodb")
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
