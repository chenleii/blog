package com.chen.blog.core.article.domain.model;

import com.chen.blog.core.account.domain.model.Account;
import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.article.domain.model.event.*;
import com.chen.blog.core.article.domain.model.exception.ArticleCommentNotExistsException;
import com.chen.blog.core.article.domain.model.exception.ArticleNotCanUpdateException;
import com.chen.blog.core.sharedkernel.ddd.annotation.AggregateRoot;
import com.chen.blog.core.sharedkernel.event.DomainEventPublisher;
import com.google.common.base.Preconditions;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

import java.time.Instant;
import java.util.*;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/6 15:27
 */
@AggregateRoot
@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public class Article implements DomainEventPublisher {

    /**
     * id
     */
    private ArticleId id;

    /**
     * 账户ID
     */
    private AccountId accountId;

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


    public static Article create(ArticleId id, AccountId accountId, String title, Set<String> tags, String content) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(accountId);
        Preconditions.checkNotNull(title);
        Preconditions.checkNotNull(content);

        tags = ObjectUtils.defaultIfNull(tags, Collections.emptySet());

        Instant now = Instant.now();
        Article article = Article.builder()
                .id(id)
                .accountId(accountId)
                .title(title)
                .tags(tags)
                .content(content)
                .status(ArticleStatus.DRAFT)
                .likes(new LinkedList<>())
                .reports(new LinkedList<>())
                .comments(new LinkedHashSet<>())
                .createdAt(now)
                .updatedAt(now)
                .build();

        article.publishEvent(
                ArticleCreatedEvent.builder()
                        .id(article.getId().getId())
                        .accountId(article.getAccountId().getId())
                        .title(article.getTitle())
                        .tags(article.getTags())
                        .content(article.getContent())
                        .status(article.getStatus())
                        .likes(article.getLikes())
                        .reports(article.getReports())
                        .comments(article.getComments())
                        .createdAt(article.getCreatedAt())
                        .updatedAt(article.getUpdatedAt())
                        .build()
        );

        return article;
    }


    public void like(Account account) {
        Preconditions.checkNotNull(account);

        checkCanUpdate(account);

        boolean liked = isLiked(account);
        getLikes().add(ArticleLikedRecord.create(account, !liked));

        if (!liked) {
            publishEvent(
                    ArticleLikedEvent.builder()
                            .id(getId().getId())
                            .accountId(getAccountId().getId())
                            .title(getTitle())
                            .tags(getTags())
                            .content(getContent())
                            .status(getStatus())
                            .likes(getLikes())
                            .reports(getReports())
                            .comments(getComments())
                            .createdAt(getCreatedAt())
                            .updatedAt(getUpdatedAt())

                            .likedAccountId(account.getId().getId())
                            .build()
            );
        } else {
            publishEvent(
                    ArticleDislikedEvent.builder()
                            .id(getId().getId())
                            .accountId(getAccountId().getId())
                            .title(getTitle())
                            .tags(getTags())
                            .content(getContent())
                            .status(getStatus())
                            .likes(getLikes())
                            .reports(getReports())
                            .comments(getComments())
                            .createdAt(getCreatedAt())
                            .updatedAt(getUpdatedAt())

                            .dislikedAccountId(account.getId().getId())
                            .build()
            );
        }

    }


    public void report(Account account, String remark) {
        Preconditions.checkNotNull(account);

        checkCanUpdate(account);

        getReports().add(ArticleReportedRecord.create(account, remark));

        publishEvent(
                ArticleReportedEvent.builder()
                        .id(getId().getId())
                        .accountId(getAccountId().getId())
                        .title(getTitle())
                        .tags(getTags())
                        .content(getContent())
                        .status(getStatus())
                        .likes(getLikes())
                        .reports(getReports())
                        .comments(getComments())
                        .createdAt(getCreatedAt())
                        .updatedAt(getUpdatedAt())

                        .reportedAccountId(account.getId().getId())
                        .remark(remark)
                        .build()
        );
    }


    private int likeNumber(Account account) {
        AccountId likeAccountId = Objects.nonNull(account) ? account.getId() : null;

        return getLikes().stream()
                .filter((item) -> Objects.isNull(likeAccountId) || Objects.equals(item.getAccountId(), likeAccountId))
                .mapToInt((item) -> item.getIsLiked() ? 1 : -1)
                .sum();

    }

    public int likeNumber() {
        return likeNumber(null);

    }

    public boolean isLiked(Account account) {
        Preconditions.checkNotNull(account);

        return likeNumber(account) > 0;
    }

    private int reportNumber(Account account) {
        AccountId reportAccountId = Objects.nonNull(account) ? account.getId() : null;
        return getReports().stream()
                .filter((item) -> Objects.isNull(reportAccountId) || Objects.equals(item.getAccountId(), reportAccountId))
                .mapToInt((item) -> 1)
                .sum();
    }

    public int reportNumber() {
        return reportNumber(null);
    }

    public boolean isReported(Account account) {
        Preconditions.checkNotNull(account);

        return reportNumber(account) > 0;
    }

    private void changeStatus(ArticleStatus status) {
        setStatus(status);

        publishEvent(
                ArticleStatusUpdatedEvent.builder()
                        .id(getId().getId())
                        .accountId(getAccountId().getId())
                        .title(getTitle())
                        .tags(getTags())
                        .content(getContent())
                        .status(getStatus())
                        .likes(getLikes())
                        .reports(getReports())
                        .comments(getComments())
                        .createdAt(getCreatedAt())
                        .updatedAt(getUpdatedAt())
                        .build()
        );
    }

    public void draft() {
        changeStatus(ArticleStatus.DRAFT);
    }

    public void publication() {
        changeStatus(ArticleStatus.PUBLICATION);
    }

    public void viewable() {
        changeStatus(ArticleStatus.VIEWABLE);
    }

    public void invisible() {
        changeStatus(ArticleStatus.INVISIBLE);
    }


    public void update(String title, Set<String> tags, String content) {
        Preconditions.checkNotNull(title);
        Preconditions.checkNotNull(content);

        tags = ObjectUtils.defaultIfNull(tags, Collections.emptySet());

        setTitle(title);
        setTags(tags);
        setContent(content);

        setUpdatedAt(Instant.now());
    }

    public void update(Account account, String title, Set<String> tags, String content) {
        Preconditions.checkNotNull(account);

        checkCanUpdate(account);

        update(title, tags, content);
    }


    /**
     * 评论
     *
     * @param account   评论的账户
     * @param commentId 评论ID
     * @param content   评论内容
     */
    public void comment(Account account, ArticleCommentId commentId, String content) {
        Preconditions.checkNotNull(account);
        Preconditions.checkNotNull(commentId);
        Preconditions.checkNotNull(content);

        checkCanUpdate(account);

        ArticleComment comment = ArticleComment.create(commentId, account.getId(), content);

        getComments().add(comment);

        publishEvent(
                ArticleCommentedEvent.builder()
                        .id(getId().getId())
                        .accountId(getAccountId().getId())
                        .title(getTitle())
                        .tags(getTags())
                        .content(getContent())
                        .status(getStatus())
                        .likes(getLikes())
                        .reports(getReports())
                        .comments(getComments())
                        .createdAt(getCreatedAt())
                        .updatedAt(getUpdatedAt())

                        .commentId(commentId.getId())
                        .commentedAccountId(account.getId().getId())
                        .build()
        );
    }

    /**
     * 回复子评论
     *
     * @param account           评论的账户
     * @param commentId         评论ID
     * @param subCommentId      子评论ID
     * @param replySubCommentId 回复的子评论ID
     * @param content           评论内容
     */
    public void replySubComment(Account account,
                                ArticleCommentId commentId, ArticleSubCommentId subCommentId,
                                ArticleSubCommentId replySubCommentId, String content) {
        Preconditions.checkNotNull(account);
        Preconditions.checkNotNull(commentId);
        Preconditions.checkNotNull(subCommentId);
        Preconditions.checkNotNull(content);

        checkCanUpdate(account);

        ArticleSubComment articleSubComment = ArticleSubComment.create(subCommentId, replySubCommentId, account.getId(), content);

        // 查找回复的评论，并进行评论。
        getComments().stream()
                .filter((item) -> Objects.equals(item.getId(), commentId))
                .findFirst()
                .orElseThrow(ArticleCommentNotExistsException::new)
                .replySubComment(articleSubComment);


        publishEvent(
                ArticleSubCommentedEvent.builder()
                        .id(getId().getId())
                        .accountId(getAccountId().getId())
                        .title(getTitle())
                        .tags(getTags())
                        .content(getContent())
                        .status(getStatus())
                        .likes(getLikes())
                        .reports(getReports())
                        .comments(getComments())
                        .createdAt(getCreatedAt())
                        .updatedAt(getUpdatedAt())

                        .commentId(commentId.getId())
                        .subCommentId(subCommentId.getId())
                        .commentedAccountId(account.getId().getId())
                        .build()
        );

    }

    public void checkCanUpdate(Account account) {
        if (getStatus() == ArticleStatus.INVISIBLE) {
            throw new ArticleNotCanUpdateException("article invisible.");
        }
        if (!Objects.equals(getAccountId(), account.getId())) {
            throw new ArticleNotCanUpdateException("account mismatch.");
        }
    }
}
