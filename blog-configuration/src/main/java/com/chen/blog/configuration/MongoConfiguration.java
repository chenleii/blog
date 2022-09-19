package com.chen.blog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/28 1:16
 */
@Configuration
public class MongoConfiguration {

    @Bean
    public PlatformTransactionManager platformTransactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}
