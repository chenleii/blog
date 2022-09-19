package com.chen.blog.core.hot.doamin.model;

import com.chen.blog.core.sharedkernel.ddd.annotation.AggregateRoot;
import com.google.common.base.Preconditions;
import lombok.*;

import java.time.Instant;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 23:35
 */
@AggregateRoot
@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public class ArticleHotSearch {

    /**
     * id
     */
    private ArticleHotSearchId id;

    /**
     * 搜索的关键词
     */
    private String keywords;

    /**
     * 搜索次数
     */
    private Integer searchNumber;

    /**
     * 热度值
     */
    private Integer heat;


    /**
     * 创建于
     */
    private Instant createdAt;
    /**
     * 更新于
     */
    private Instant updatedAt;

    public static ArticleHotSearch create(ArticleHotSearchId id, String keywords) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(keywords);

        Instant now = Instant.now();
        return ArticleHotSearch.builder()
                .id(id)
                .keywords(keywords)
                .searchNumber(0)
                .heat(1)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void search() {
        setSearchNumber(getSearchNumber() + 1);
        setHeat(getHeat() * 2);

        setUpdatedAt(Instant.now());
    }
}
