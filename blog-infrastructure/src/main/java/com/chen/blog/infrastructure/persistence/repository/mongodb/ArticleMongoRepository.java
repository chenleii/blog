package com.chen.blog.infrastructure.persistence.repository.mongodb;

import com.chen.blog.infrastructure.persistence.repository.dataobject.ArticleDO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 15:43
 */
public interface ArticleMongoRepository extends MongoRepository<ArticleDO,Long> {
}
