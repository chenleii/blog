package com.chen.blog.core.article.domain.model.event;

import com.chen.blog.core.sharedkernel.event.DomainEvent;
import com.chen.blog.core.article.domain.model.*;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:10
 */
@SuperBuilder
@Getter
@ToString(callSuper = true)
public abstract class AbstractArticleEvent extends DomainEvent {

    /**
     * id
     */
    private Long id;

    /**
     * 账户ID
     */
    private Long accountId;

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
    /**
     * 喜欢记录
     */
    private List<ArticleLikedRecord> likes;
    /**
     * 举报记录
     */
    private List<ArticleReportedRecord> reports;

    /**
     * 评论
     */
    private Set<ArticleComment> comments;

    /**
     * 创建于
     */
    private Instant createdAt;
    /**
     * 更新于
     */
    private Instant updatedAt;
}
