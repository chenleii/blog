package com.chen.blog.interfaces.http.controller;

import com.chen.blog.core.account.application.commandservice.AccountCommandService;
import com.chen.blog.core.account.application.queryservice.AccountQueryService;
import com.chen.blog.core.account.domain.model.context.LoggedInAccount;
import com.chen.blog.core.account.domain.model.cqrs.command.AccountLoginCommand;
import com.chen.blog.core.account.domain.model.cqrs.command.AccountRefreshLoginCommand;
import com.chen.blog.core.account.domain.model.cqrs.command.AccountUpdateCommand;
import com.chen.blog.core.account.domain.model.cqrs.command.SendLoginVerificationCodeCommand;
import com.chen.blog.core.sharedkernel.trace.Trace;
import com.chen.blog.core.sharedkernel.trace.TraceMonitorLog;
import com.chen.blog.interfaces.http.dto.input.AccountLoginInputDTO;
import com.chen.blog.interfaces.http.dto.input.AccountSendLoginVerificationCodeInputDTO;
import com.chen.blog.interfaces.http.dto.input.AccountUpdateInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/8 15:24
 */
@Trace
@TraceMonitorLog
@Tag(name = "account-api",description = "账户接口")
@Slf4j
@RestController
@RequestMapping("/api/blog/account")
public class AccountAppController extends AbstractAppController {


    @Inject
    private AccountCommandService accountCommandService;
    @Inject
    private AccountQueryService accountQueryService;


    @Operation(summary = "发送登录验证码")
    @PostMapping("/sendLoginVerificationCode")
    public void sendLoginVerificationCode(@RequestBody AccountSendLoginVerificationCodeInputDTO dto) {
        accountCommandService.sendLoginVerificationCode(
                SendLoginVerificationCodeCommand.builder()
                        .phoneNo(dto.getPhoneNo())
                        .build()
        );
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public LoggedInAccount login(@RequestBody AccountLoginInputDTO dto) {
        LoggedInAccount loggedInAccount = accountCommandService.login(
                AccountLoginCommand.builder()
                        .phoneNo(dto.getPhoneNo())
                        .verificationCode(dto.getVerificationCode())
                        .build()
        );

        super.login(loggedInAccount);
        return loggedInAccount;
    }

    @Operation(summary = "刷新登录")
    @PostMapping("/refreshLogin")
    public LoggedInAccount refreshLogin() {
        Long currentLoggedInAccountId = super.getCurrentLoggedInAccountId();

        LoggedInAccount loggedInAccount = accountCommandService.refreshLogin(
                AccountRefreshLoginCommand.builder()
                        .accountId(currentLoggedInAccountId)
                        .build()
        );
        super.login(loggedInAccount);
        return loggedInAccount;
    }

    @Operation(summary = "获取登录账户")
    @GetMapping("/login")
    public LoggedInAccount getLoggedInAccount() {
        return getCurrentLoggedInAccount();
    }


    @Operation(summary = "登出")
    @PostMapping("/logout")
    public void logout() {
        super.logout();
    }


    @Operation(summary = "更新")
    @PostMapping("/update")
    public void update(@RequestBody AccountUpdateInputDTO dto) {
        Long currentLoggedInAccountId = super.getCurrentLoggedInAccountId();
        accountCommandService.update(
                AccountUpdateCommand.builder()
                        .accountId(currentLoggedInAccountId)
                        .avatar(dto.getAvatar())
                        .name(dto.getName())
                        .password(dto.getPassword())
                        .introduction(dto.getIntroduction())
                        .build()
        );

        LoggedInAccount loggedInAccount = accountCommandService.refreshLogin(
                AccountRefreshLoginCommand.builder()
                        .accountId(currentLoggedInAccountId)
                        .build()
        );
        super.login(loggedInAccount);
    }
}
