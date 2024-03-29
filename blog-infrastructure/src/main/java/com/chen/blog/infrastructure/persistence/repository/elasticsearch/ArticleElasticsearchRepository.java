package com.chen.blog.infrastructure.persistence.repository.elasticsearch;

import com.chen.blog.infrastructure.persistence.repository.dataobject.ArticleDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import jakarta.inject.Named;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 15:43
 */
@Named
public interface ArticleElasticsearchRepository extends ElasticsearchRepository<ArticleDO,Long> {
}
