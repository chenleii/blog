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
@org.springframework.data.mongodb.core.mapping.Document(collection = "article-hot")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "article-hot")
public class ArticleHotDO implements Serializable {

    /**
     * 文章ID
     */
    @Id
    private Long articleId;


    /**
     * 访问次数
     */
    @Indexed
    private Integer visitedNumber;
    /**
     * 喜欢的次数
     */
    @Indexed
    private Integer likedNumber;
    /**
     * 举报的次数
     */
    @Indexed
    private Integer reportedNumber;
    /**
     * 评论的次数
     */
    @Indexed
    private Integer commentedNumber;

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
