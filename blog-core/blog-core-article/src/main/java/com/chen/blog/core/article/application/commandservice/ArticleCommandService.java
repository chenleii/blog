package com.chen.blog.core.article.application.commandservice;

import com.chen.blog.core.account.application.commandservice.AccountCommandService;
import com.chen.blog.core.account.domain.model.Account;
import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.account.domain.model.cqrs.command.AccountMediaAvailableCheckCommand;
import com.chen.blog.core.account.domain.model.repository.AccountRepository;
import com.chen.blog.core.article.domain.model.*;
import com.chen.blog.core.article.domain.model.cqrs.command.*;
import com.chen.blog.core.article.domain.model.exception.ArticleRiskControlException;
import com.chen.blog.core.article.domain.model.repository.ArticleRepository;
import com.chen.blog.core.article.domain.model.service.ArticleService;
import com.chen.blog.core.sharedkernel.cqrs.annotation.CommandService;
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
import java.util.Set;

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
public class ArticleCommandService {

    @Inject
    private ArticleRepository articleRepository;
    @Inject
    private AccountRepository accountRepository;

    @Inject
    private ArticleService articleService;

    @Inject
    private AccountCommandService accountCommandService;


    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class, noRollbackFor = ArticleRiskControlException.class)
    public Long save(@Valid ArticleSaveAndPublishCommand command) {
        AccountId accountId = AccountId.of(command.getAccountId());
        String title = command.getTitle();
        Set<String> tags = command.getTags();
        String content = command.getContent();

        accountCommandService.checkAvailable(AccountMediaAvailableCheckCommand.builder().accountId(accountId.getId()).build());

        ArticleId articleId = ArticleId.generateId();
        Article article = Article.create(articleId, accountId, title, tags, content);

        articleService.save(article, command.getIsPublish());

        articleRepository.save(article);

        return articleId.getId();
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class, noRollbackFor = ArticleRiskControlException.class)
    public Long update(@Valid ArticleUpdateAndPublishCommand command) {
        ArticleId articleId = ArticleId.of(command.getArticleId());
        AccountId accountId = AccountId.of(command.getAccountId());
        String title = command.getTitle();
        Set<String> tags = command.getTags();
        String content = command.getContent();
        Boolean isPublish = command.getIsPublish();

        accountCommandService.checkAvailable(AccountMediaAvailableCheckCommand.builder().accountId(accountId.getId()).build());

        Article article = articleRepository.getByIdNotExistsThrowException(articleId);
        Account account = accountRepository.getByIdNotExistsThrowException(accountId);

        articleService.update(account, article, title, tags, content, isPublish);

        articleRepository.update(article);

        return articleId.getId();
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public int like(@Valid ArticleLikeCommand command) {
        ArticleId articleId = ArticleId.of(command.getArticleId());
        AccountId accountId = AccountId.of(command.getAccountId());

        Account account = accountRepository.getByIdNotExistsThrowException(accountId);
        Article article = articleRepository.getByIdNotExistsThrowException(articleId);

        article.like(account);

        articleRepository.update(article);

        return article.likeNumber();
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public int report(@Valid ArticleReportCommand command) {
        ArticleId articleId = ArticleId.of(command.getArticleId());
        AccountId accountId = AccountId.of(command.getAccountId());
        String remark = command.getRemark();

        Account account = accountRepository.getByIdNotExistsThrowException(accountId);
        Article article = articleRepository.getByIdNotExistsThrowException(articleId);

        articleService.report(article, account, remark);

        articleRepository.update(article);

        return article.reportNumber();
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public Long comment(@Valid ArticleCommentCommand command) {
        ArticleId articleId = ArticleId.of(command.getArticleId());
        AccountId accountId = AccountId.of(command.getAccountId());
        String content = command.getContent();

        Account account = accountRepository.getByIdNotExistsThrowException(accountId);
        Article article = articleRepository.getByIdNotExistsThrowException(articleId);

        ArticleCommentId commentId = ArticleCommentId.generateId();
        article.comment(account, commentId, content);

        articleRepository.update(article);

        return commentId.getId();
    }

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public Long replySubComment(@Valid ArticleReplySubCommentCommand command) {
        ArticleId articleId = ArticleId.of(command.getArticleId());
        AccountId accountId = AccountId.of(command.getAccountId());
        ArticleCommentId commentId = Objects.nonNull(command.getCommentId()) ? ArticleCommentId.of(command.getCommentId()) : null;
        ArticleSubCommentId replySubCommentId = Objects.nonNull(command.getReplyCommentId()) ? ArticleSubCommentId.of(command.getReplyCommentId()) : null;
        String content = command.getContent();

        Account account = accountRepository.getByIdNotExistsThrowException(accountId);
        Article article = articleRepository.getByIdNotExistsThrowException(articleId);

        ArticleSubCommentId articleSubCommentId = ArticleSubCommentId.generateId();
        article.replySubComment(account, commentId, articleSubCommentId, replySubCommentId, content);

        articleRepository.update(article);

        return commentId.getId();
    }


}
