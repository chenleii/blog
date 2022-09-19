package com.chen.blog.core.hot.application.eventlistener;

import com.chen.blog.core.hot.application.commandservice.ArticleHotCommandService;
import com.chen.blog.core.hot.doamin.model.cqrs.command.*;
import com.chen.blog.core.article.domain.model.ArticleStatus;
import com.chen.blog.core.article.domain.model.event.*;
import com.chen.blog.core.sharedkernel.trace.TraceMonitorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:09
 */
@TraceMonitorLog
@Slf4j
@Named
public class ArticleHotEventListener {

    @Inject
    private ArticleHotCommandService articleHotCommandService;

    @Async
    @TransactionalEventListener
    public void eventListener(ArticleLikedEvent event) {
        articleHotCommandService.like(
                ArticleLikeCommand.builder()
                        .articleId(event.getId())
                        .accountId(event.getLikedAccountId())
                        .build()
        );

    }

    @Async
    @TransactionalEventListener
    public void eventListener(ArticleDislikedEvent event) {

        articleHotCommandService.dislike(
                ArticleDislikeCommand.builder()
                        .articleId(event.getId())
                        .accountId(event.getDislikedAccountId())
                        .build()
        );
    }

    @Async
    @TransactionalEventListener
    public void eventListener(ArticleReportedEvent event) {

        articleHotCommandService.report(
                ArticleReportCommand.builder()
                        .articleId(event.getId())
                        .accountId(event.getReportedAccountId())
                        .remark(event.getRemark())
                        .build()
        );
    }

    @Async
    @TransactionalEventListener
    public void eventListener(ArticleCommentedEvent event) {
        articleHotCommandService.comment(
                ArticleCommentCommand.builder()
                        .articleId(event.getId())
                        .accountId(event.getCommentedAccountId())
                        .commentId(event.getCommentId())
                        .subCommentId(null)
                        .build()
        );
    }

    @Async
    @TransactionalEventListener
    public void eventListener(ArticleSubCommentedEvent event) {
        articleHotCommandService.comment(
                ArticleCommentCommand.builder()
                        .articleId(event.getId())
                        .accountId(event.getCommentedAccountId())
                        .commentId(event.getCommentId())
                        .subCommentId(event.getSubCommentId())
                        .build()
        );
    }
    @Async
    @TransactionalEventListener
    public void eventListener(ArticleStatusUpdatedEvent event) {
        if (event.getStatus() == ArticleStatus.INVISIBLE) {
            articleHotCommandService.delete(
                    ArticleHotDeleteCommand.builder()
                            .articleId(event.getId())
                            .build()
            );
        }

    }
}
