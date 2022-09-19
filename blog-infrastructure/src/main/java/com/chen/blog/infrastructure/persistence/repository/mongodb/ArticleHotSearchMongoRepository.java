package com.chen.blog.infrastructure.persistence.repository.mongodb;

import com.chen.blog.infrastructure.persistence.repository.dataobject.ArticleHotSearchDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 15:43
 */
public interface ArticleHotSearchMongoRepository extends MongoRepository<ArticleHotSearchDO, Long> {


    /**
     * 根据搜索关键词查询
     *
     * @param keywords 搜索关键词
     * @return 文章热搜
     */
    Optional<ArticleHotSearchDO> findByKeywords(String keywords);

}
