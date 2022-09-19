package com.chen.blog.core.article.domain.model.change;

import com.chen.blog.core.article.domain.model.Article;
import com.chen.blog.core.article.domain.model.ArticleStatus;
import com.chen.blog.core.sharedkernel.ddd.annotation.ValueObject;
import lombok.*;

import java.util.Set;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 11:37
 */
@ValueObject
@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public class ArticleSnapshot {
    /**
     * 标题
     */
    private String title;
    /**
     * 标签
     */
    private Set<String> tags;
    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private ArticleStatus status;


    public static ArticleSnapshot create(Article article) {
        return ArticleSnapshot.builder()
                .title(article.getTitle())
                .tags(article.getTags())
                .content(article.getContent())
                .status(article.getStatus())
                .build();
    }

}
