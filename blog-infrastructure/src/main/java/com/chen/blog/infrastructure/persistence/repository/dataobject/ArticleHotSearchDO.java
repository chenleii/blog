package com.chen.blog.infrastructure.persistence.repository.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;

/**
 *
 * @author cl
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@org.springframework.data.mongodb.core.mapping.Document(collection = "article-hot-search")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "article-hot-search")
public class ArticleHotSearchDO implements Serializable {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 搜索的关键词
     */
    @Indexed(unique = true)
    private String keywords;

    /**
     * 搜索次数
     */
    @Indexed
    private Integer searchNumber;

    /**
     * 热度值
     */
    @Indexed
    private Integer heat;


    /**
     * 创建于
     */
    @Indexed
    private Long createdAt;
    /**
     * 更新于
     */
    @Indexed
    private Long updatedAt;

}
