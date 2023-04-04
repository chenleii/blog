package com.chen.blog.core.article.application.eventlistener;

import com.chen.blog.core.account.application.commandservice.AccountCommandService;
import com.chen.blog.core.account.domain.model.cqrs.command.AccountDisableCommand;
import com.chen.blog.core.article.application.commandservice.ArticleCommandService;
import com.chen.blog.core.article.domain.model.ArticleStatus;
import com.chen.blog.core.article.domain.model.event.ArticleStatusUpdatedEvent;
import com.chen.blog.core.sharedkernel.tracer.TraceMonitorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:09
 */
@TraceMonitorLog
@Slf4j
@Named
public class ArticleEventListener {

    @Inject
    private ArticleCommandService articleCommandService;
    @Inject
    private AccountCommandService accountCommandService;


    @Async
    @TransactionalEventListener
    public void eventListener(ArticleStatusUpdatedEvent event) {
        if (event.getStatus() == ArticleStatus.INVISIBLE) {
            // 禁用账户
            accountCommandService.disable(
                    AccountDisableCommand.builder()
                            .accountId(event.getAccountId())
                            .build());
        }
    }

}
