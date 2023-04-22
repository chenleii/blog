package com.chen.blog.core.notification.application.commandservice;

import com.chen.blog.core.account.domain.model.Account;
import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.account.domain.model.repository.AccountRepository;
import com.chen.blog.core.article.application.queryservice.ArticleQueryService;
import com.chen.blog.core.article.domain.model.cqrs.query.ArticleQuery;
import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.core.notification.domain.model.Notification;
import com.chen.blog.core.notification.domain.model.NotificationDetails;
import com.chen.blog.core.notification.domain.model.NotificationDetailsAdditionalInfo;
import com.chen.blog.core.notification.domain.model.NotificationId;
import com.chen.blog.core.notification.domain.model.cqrs.command.*;
import com.chen.blog.core.notification.domain.model.repository.NotificationRepository;
import com.chen.blog.core.sharedkernel.cqrs.annotation.CommandService;
import com.chen.blog.core.sharedkernel.tracer.TraceMonitorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:09
 */
@TraceMonitorLog
@CommandService
@Slf4j
@Named
@Validated
public class NotificationCommandService {

    @Inject
    private NotificationRepository notificationRepository;
    @Inject
    private AccountRepository accountRepository;


    @Inject
    private ArticleQueryService articleQueryService;

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public Long accountDisabled(NotificationAccountDisabledCommand command) {
        Long disabledAccountId = command.getDisabledAccountId();

        Account account = accountRepository.getByIdNotExistsThrowException(AccountId.of(disabledAccountId));

        NotificationDetailsAdditionalInfo.AccountDisabled detailsAdditionalInfo = NotificationDetailsAdditionalInfo.AccountDisabled.builder()
                .accountId(disabledAccountId)
                .accountName(account.getName())
                .build();
        NotificationDetails details = NotificationDetails.accountDisabled(detailsAdditionalInfo);
        Notification notification = Notification.create(
                NotificationId.generateId(),
                AccountId.of(disabledAccountId),
                details
        );
        notificationRepository.save(notification);
        return notification.getId().getId();
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public Long articleLiked(@Valid NotificationArticleLikedCommand command) {
        Long articleId = command.getArticleId();
        Long likedAccountId = command.getLikedAccountId();

        ArticleRepresentation articleRepresentation = articleQueryService.query(ArticleQuery.builder().articleId(articleId).build());
        Account account = accountRepository.getByIdNotExistsThrowException(AccountId.of(likedAccountId));

        NotificationDetailsAdditionalInfo.ArticleLiked detailsAdditionalInfo = NotificationDetailsAdditionalInfo.ArticleLiked.builder()
                .articleId(articleId)
                .articleTitle(articleRepresentation.getTitle())
                .accountId(likedAccountId)
                .accountName(account.getName())
                .build();
        NotificationDetails details = NotificationDetails.articleLiked(detailsAdditionalInfo);
        Notification notification = Notification.create(
                NotificationId.generateId(),
                AccountId.of(articleRepresentation.getAccountId()),
                details
        );
        notificationRepository.save(notification);
        return notification.getId().getId();
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public Long articleComment(@Valid NotificationArticleCommentCommand command) {
        Long articleId = command.getArticleId();
        Long commentId = command.getCommentId();
        Long subCommentId = command.getSubCommentId();
        Long replySubCommentId = command.getReplySubCommentId();
        Long commentAccountId = command.getCommentAccountId();

        ArticleRepresentation articleRepresentation = articleQueryService.query(ArticleQuery.builder().articleId(articleId).build());
        Account account = accountRepository.getByIdNotExistsThrowException(AccountId.of(commentAccountId));

        NotificationDetailsAdditionalInfo.ArticleComment detailsAdditionalInfo = NotificationDetailsAdditionalInfo.ArticleComment.builder()
                .articleId(articleId)
                .articleTitle(articleRepresentation.getTitle())
                .articleCommentId(commentId)
                .articleSubCommentId(subCommentId)
                .articleReplySubCommentId(replySubCommentId)
                .accountId(commentAccountId)
                .accountName(account.getName())
                .atAccountIds(List.of())
                .build();
        NotificationDetails details = NotificationDetails.articleComment(detailsAdditionalInfo);
        Notification notification = Notification.create(
                NotificationId.generateId(),
                AccountId.of(articleRepresentation.getAccountId()),
                details
        );
        notificationRepository.save(notification);
        return notification.getId().getId();
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public void read(NotificationReadCommand command) {
        Long notificationId = command.getNotificationId();

        Notification notification = notificationRepository.getByIdNotExistsThrowException(NotificationId.of(notificationId));

        notification.read();
        notificationRepository.update(notification);
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public void clear(NotificationClearCommand command) {
        Long notificationId = command.getNotificationId();

        Notification notification = notificationRepository.getByIdNotExistsThrowException(NotificationId.of(notificationId));

        notification.clear();
        notificationRepository.update(notification);
    }

}
