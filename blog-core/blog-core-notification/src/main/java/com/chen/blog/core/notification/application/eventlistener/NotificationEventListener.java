package com.chen.blog.core.notification.application.eventlistener;

import com.chen.blog.core.account.domain.model.event.AccountDisabledEvent;
import com.chen.blog.core.article.domain.model.event.ArticleCommentedEvent;
import com.chen.blog.core.article.domain.model.event.ArticleLikedEvent;
import com.chen.blog.core.article.domain.model.event.ArticleSubCommentedEvent;
import com.chen.blog.core.notification.application.commandservice.NotificationCommandService;
import com.chen.blog.core.notification.domain.model.cqrs.command.NotificationAccountDisabledCommand;
import com.chen.blog.core.notification.domain.model.cqrs.command.NotificationArticleCommentCommand;
import com.chen.blog.core.notification.domain.model.cqrs.command.NotificationArticleLikedCommand;
import com.chen.blog.core.sharedkernel.tracer.TraceMonitorLog;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author cl
 * @version 1.0
 * @since 2023/4/12 20:09
 */
@TraceMonitorLog
@Slf4j
@Named
public class NotificationEventListener {

    @Inject
    private NotificationCommandService notificationCommandService;

    @Async
    @TransactionalEventListener
    public void eventListener(AccountDisabledEvent event) {
        notificationCommandService.accountDisabled(
                NotificationAccountDisabledCommand.builder()
                        .disabledAccountId(event.getId())
                        .build()
        );
    }
    @Async
    @TransactionalEventListener
    public void eventListener(ArticleLikedEvent event) {
        notificationCommandService.articleLiked(
                NotificationArticleLikedCommand.builder()
                        .articleId(event.getId())
                        .likedAccountId(event.getLikedAccountId())
                        .build()
        );
    }



    @Async
    @TransactionalEventListener
    public void eventListener(ArticleCommentedEvent event) {
        notificationCommandService.articleComment(
                NotificationArticleCommentCommand.builder()
                        .articleId(event.getId())
                        .commentId(event.getCommentId())
                        .subCommentId(null)
                        .replySubCommentId(null)
                        .commentAccountId(event.getCommentedAccountId())
                        .build()
        );
    }

    @Async
    @TransactionalEventListener
    public void eventListener(ArticleSubCommentedEvent event) {
        notificationCommandService.articleComment(
                NotificationArticleCommentCommand.builder()
                        .articleId(event.getId())
                        .commentId(event.getCommentId())
                        .subCommentId(event.getSubCommentId())
                        .replySubCommentId(event.getReplySubCommentId())
                        .commentAccountId(event.getCommentedAccountId())
                        .build()
        );
    }
}
