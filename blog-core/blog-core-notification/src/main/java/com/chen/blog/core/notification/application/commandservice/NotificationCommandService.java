package com.chen.blog.core.notification.application.commandservice;

import com.chen.blog.core.account.application.commandservice.AccountCommandService;
import com.chen.blog.core.account.domain.model.repository.AccountRepository;
import com.chen.blog.core.notification.domain.model.cqrs.command.NotificationCreateCommand;
import com.chen.blog.core.notification.domain.model.repository.NotificationRepository;
import com.chen.blog.core.sharedkernel.cqrs.annotation.CommandService;
import com.chen.blog.core.sharedkernel.trace.TraceMonitorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;

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
    private AccountCommandService accountCommandService;


    @Transactional(rollbackFor = Exception.class)
    public Long create(@Valid NotificationCreateCommand command) {

        return null;
    }


}
