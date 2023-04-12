package com.chen.blog.interfaces.http.controller;

import com.chen.blog.core.account.application.commandservice.AccountCommandService;
import com.chen.blog.core.account.application.queryservice.AccountQueryService;
import com.chen.blog.core.account.domain.model.context.LoggedInAccount;
import com.chen.blog.core.account.domain.model.cqrs.command.AccountLoginCommand;
import com.chen.blog.core.account.domain.model.cqrs.command.AccountRefreshLoginCommand;
import com.chen.blog.core.account.domain.model.cqrs.command.AccountUpdateCommand;
import com.chen.blog.core.account.domain.model.cqrs.command.SendLoginVerificationCodeCommand;
import com.chen.blog.core.article.domain.model.ArticleStatus;
import com.chen.blog.core.article.domain.model.cqrs.query.ArticlePageQuery;
import com.chen.blog.core.article.domain.model.cqrs.query.ArticleQuery;
import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.core.hot.doamin.model.cqrs.command.ArticleAccessCommand;
import com.chen.blog.core.hot.doamin.model.cqrs.command.ArticleSearchCommand;
import com.chen.blog.core.notification.application.commandservice.NotificationCommandService;
import com.chen.blog.core.notification.application.queryservice.NotificationQueryService;
import com.chen.blog.core.notification.domain.model.Notification;
import com.chen.blog.core.notification.domain.model.NotificationStatus;
import com.chen.blog.core.notification.domain.model.cqrs.command.NotificationClearCommand;
import com.chen.blog.core.notification.domain.model.cqrs.command.NotificationReadCommand;
import com.chen.blog.core.notification.domain.model.cqrs.query.NotificationPageQuery;
import com.chen.blog.core.notification.domain.model.cqrs.query.NotificationQuery;
import com.chen.blog.core.notification.domain.model.cqrs.representation.NotificationRepresentation;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.core.sharedkernel.tracer.OpenTracer;
import com.chen.blog.core.sharedkernel.tracer.TraceMonitorLog;
import com.chen.blog.interfaces.http.dto.input.AccountLoginInputDTO;
import com.chen.blog.interfaces.http.dto.input.AccountSendLoginVerificationCodeInputDTO;
import com.chen.blog.interfaces.http.dto.input.AccountUpdateInputDTO;
import com.chen.blog.interfaces.http.dto.input.NotificationPageQueryInputDTO;
import com.google.common.collect.Sets;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author cl
 * @version 1.0
 * @since 2023/4/12 23:50
 */
@OpenTracer
@TraceMonitorLog
@Tag(name = "notification-api", description = "通知接口")
@Slf4j
@RestController
@RequestMapping("/api/blog/notification")
public class NotificationAppController extends AbstractAppController {


    @Inject
    private NotificationCommandService notificationCommandService;
    @Inject
    private NotificationQueryService notificationQueryService;


    @Operation(summary = "分页查询")
    @GetMapping("")
    public Pagination<NotificationRepresentation> pageQuery(@ModelAttribute NotificationPageQueryInputDTO dto) {
        Long currentLoggedInAccountId = super.getCurrentLoggedInAccountId();
        return notificationQueryService.pageQuery(
                NotificationPageQuery.builder()
                        .pageIndex(dto.getPageIndex())
                        .pageSize(dto.getPageSize())
                        .accountId(currentLoggedInAccountId)
                        .statuses(Sets.newHashSet(NotificationStatus.UNREAD, NotificationStatus.HAS_READ))
                        .build());
    }

    @Operation(summary = "查询")
    @GetMapping("/{notificationId}")
    public NotificationRepresentation query(@PathVariable(name = "notificationId") Long notificationId) {
        return notificationQueryService.query(NotificationQuery.builder().notificationId(notificationId).build());
    }


    @Operation(summary = "读")
    @PostMapping("/{notificationId}/read")
    public void read(@PathVariable(name = "notificationId") Long notificationId) {
        Long currentLoggedInAccountId = super.getCurrentLoggedInAccountId();

        notificationCommandService.read(
                NotificationReadCommand.builder()
                        .notificationId(notificationId)
                        .build()
        );
    }
    @Operation(summary = "清除")
    @PostMapping("/{notificationId}/clear")
    public void clear(@PathVariable(name = "notificationId") Long notificationId) {
        Long currentLoggedInAccountId = super.getCurrentLoggedInAccountId();

        notificationCommandService.clear(
                NotificationClearCommand.builder()
                        .notificationId(notificationId)
                        .build()
        );
    }
}
