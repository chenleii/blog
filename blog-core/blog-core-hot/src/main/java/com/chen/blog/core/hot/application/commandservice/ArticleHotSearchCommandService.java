package com.chen.blog.core.hot.application.commandservice;

import com.chen.blog.core.account.domain.model.repository.AccountRepository;
import com.chen.blog.core.hot.doamin.model.ArticleHotSearch;
import com.chen.blog.core.hot.doamin.model.ArticleHotSearchId;
import com.chen.blog.core.hot.doamin.model.cqrs.command.*;
import com.chen.blog.core.hot.doamin.model.repository.ArticleHotSearchRepository;
import com.chen.blog.core.article.domain.model.repository.ArticleRepository;
import com.chen.blog.core.sharedkernel.cqrs.annotation.CommandService;
import com.chen.blog.core.sharedkernel.lock.Locks;
import com.chen.blog.core.sharedkernel.trace.TraceMonitorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
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
public class ArticleHotSearchCommandService {

    @Inject
    private ArticleHotSearchRepository articleHotSearchRepository;

    @Inject
    private ArticleRepository articleRepository;
    @Inject
    private AccountRepository accountRepository;

    @Retryable(exceptionExpression = "message.contains('WriteConflict')", maxAttempts = 10, backoff = @Backoff(delay = 1))
    @Transactional(rollbackFor = Exception.class)
    public void hotSearch(@Valid ArticleSearchCommand command) {
        String keywords = command.getKeywords();

        handle(keywords, (ArticleHotSearch::search));
    }


    private void handle(String keywords, Consumer<ArticleHotSearch> consumer) {
        Lock lock = Locks.current().create(this.getClass().getName());

        lock.lock();

        try {
            ArticleHotSearch articleHotSearch = articleHotSearchRepository.getByKeywords(keywords);
            if (Objects.isNull(articleHotSearch)) {
                ArticleHotSearchId articleHotSearchId = ArticleHotSearchId.generateId();
                articleHotSearch = ArticleHotSearch.create(articleHotSearchId, keywords);

                consumer.accept(articleHotSearch);
                articleHotSearchRepository.save(articleHotSearch);
            } else {

                consumer.accept(articleHotSearch);
                articleHotSearchRepository.update(articleHotSearch);
            }
        } finally {
            lock.unlock();
        }
    }
}
