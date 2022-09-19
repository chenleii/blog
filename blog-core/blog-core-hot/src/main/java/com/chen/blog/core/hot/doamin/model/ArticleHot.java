package com.chen.blog.core.hot.doamin.model;

import com.chen.blog.core.article.domain.model.ArticleId;
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
public class ArticleHot {

    /**
     * 文章ID
     */
    private ArticleId articleId;


    /**
     * 访问次数
     */
    private Integer visitedNumber;
    /**
     * 喜欢的次数
     */
    private Integer likedNumber;
    /**
     * 举报的次数
     */
    private Integer reportedNumber;
    /**
     * 评论的次数
     */
    private Integer commentedNumber;

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

    public static ArticleHot create(ArticleId articleId) {
        Preconditions.checkNotNull(articleId);

        Instant now = Instant.now();
        return ArticleHot.builder()
                .articleId(articleId)
                .visitedNumber(0)
                .likedNumber(0)
                .reportedNumber(0)
                .commentedNumber(0)
                .heat(1)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void access() {
        setVisitedNumber(getVisitedNumber() + 1);
        setHeat(getHeat() + 1);

        setUpdatedAt(Instant.now());
    }

    public void like() {
        setLikedNumber(getLikedNumber() + 1);
        setHeat(getHeat() * 2);

        setUpdatedAt(Instant.now());
    }

    public void dislike() {
        setLikedNumber(getLikedNumber() - 1);
        setHeat(getHeat() / 2);

        setUpdatedAt(Instant.now());
    }

    public void report() {
        setReportedNumber(getReportedNumber() + 1);
        setHeat(getHeat() / 3);

        setUpdatedAt(Instant.now());
    }

    public void comment() {
        setCommentedNumber(getCommentedNumber() + 1);
        setHeat(getHeat() * 2);

        setUpdatedAt(Instant.now());
    }
}
