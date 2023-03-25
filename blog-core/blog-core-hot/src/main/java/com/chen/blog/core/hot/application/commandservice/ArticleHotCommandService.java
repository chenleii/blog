package com.chen.blog.core.hot.application.commandservice;

import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.account.domain.model.repository.AccountRepository;
import com.chen.blog.core.hot.doamin.model.ArticleHot;
import com.chen.blog.core.hot.doamin.model.cqrs.command.*;
import com.chen.blog.core.hot.doamin.model.repository.ArticleHotRepository;
import com.chen.blog.core.article.domain.model.ArticleId;
import com.chen.blog.core.article.domain.model.repository.ArticleRepository;
import com.chen.blog.core.sharedkernel.cqrs.annotation.CommandService;
import com.chen.blog.core.sharedkernel.lock.Locks;
import com.chen.blog.core.sharedkernel.trace.TraceMonitorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/22 23:47
 */
@TraceMonitorLog
@CommandService
@Slf4j
@Named
@Validated
public class ArticleHotCommandService {

    @Inject
    private ArticleHotRepository articleHotRepository;

    @Inject
    private ArticleRepository articleRepository;
    @Inject
    private AccountRepository accountRepository;



    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public void access(@Valid ArticleAccessCommand command) {
        ArticleId articleId = ArticleId.of(command.getArticleId());
        AccountId accountId = Objects.nonNull(command.getAccountId()) ? AccountId.of(command.getAccountId()) : null;

        handle(articleId, (ArticleHot::access));
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public void like(@Valid ArticleLikeCommand command) {
        ArticleId articleId = ArticleId.of(command.getArticleId());
        AccountId accountId = Objects.nonNull(command.getAccountId()) ? AccountId.of(command.getAccountId()) : null;


        handle(articleId, (ArticleHot::like));
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public void dislike(@Valid ArticleDislikeCommand command) {
        ArticleId articleId = ArticleId.of(command.getArticleId());
        AccountId accountId = Objects.nonNull(command.getAccountId()) ? AccountId.of(command.getAccountId()) : null;


        handle(articleId, (ArticleHot::dislike));
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public void report(@Valid ArticleReportCommand command) {
        ArticleId articleId = ArticleId.of(command.getArticleId());
        AccountId accountId = Objects.nonNull(command.getAccountId()) ? AccountId.of(command.getAccountId()) : null;

        handle(articleId, (ArticleHot::report));
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public void comment(@Valid ArticleCommentCommand command) {
        ArticleId articleId = ArticleId.of(command.getArticleId());
        AccountId accountId = Objects.nonNull(command.getAccountId()) ? AccountId.of(command.getAccountId()) : null;

        handle(articleId, (ArticleHot::comment));
    }


    private void handle(ArticleId articleId, Consumer<ArticleHot> consumer) {
        Lock lock = Locks.current().create(this.getClass().getName());

        lock.lock();

        try {
            ArticleHot articleHot = articleHotRepository.getByArticleId(articleId);
            if (Objects.isNull(articleHot)) {
                articleHot = ArticleHot.create(articleId);

                consumer.accept(articleHot);
                articleHotRepository.save(articleHot);
            } else {

                consumer.accept(articleHot);
                articleHotRepository.update(articleHot);
            }
        } finally {
            lock.unlock();
        }
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public void delete(@Valid ArticleHotDeleteCommand command) {
        ArticleId articleId = ArticleId.of(command.getArticleId());

        articleHotRepository.deleteById(articleId);
    }
}
