package com.chen.blog.infrastructure.persistence.repository.mongodb;

import com.chen.blog.infrastructure.persistence.repository.dataobject.ArticleDO;
import com.chen.blog.infrastructure.persistence.repository.dataobject.NotificationDO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author cl
 * @version 1.0
 * @since 2023/4/12 23:33
 */
public interface NotificationMongoRepository extends MongoRepository<NotificationDO,Long> {
}
