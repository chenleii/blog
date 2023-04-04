package com.chen.blog.core.account.application.commandservice;

import com.chen.blog.core.account.domain.model.Account;
import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.account.domain.model.context.LoggedInAccount;
import com.chen.blog.core.account.domain.model.cqrs.command.*;
import com.chen.blog.core.account.domain.model.repository.AccountRepository;
import com.chen.blog.core.account.domain.model.service.AccountService;
import com.chen.blog.core.account.domain.model.service.PhoneVerificationCodeService;
import com.chen.blog.core.sharedkernel.cqrs.annotation.CommandService;
import com.chen.blog.core.sharedkernel.tracer.TraceMonitorLog;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.inject.Inject;
import jakarta.validation.Valid;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 21:45
 */
@TraceMonitorLog
@CommandService
@Slf4j
@Named
@Validated
public class AccountCommandService {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private AccountService accountService;
    @Inject
    private PhoneVerificationCodeService phoneVerificationCodeService;


    public void sendLoginVerificationCode(@Valid SendLoginVerificationCodeCommand command) {
        String phoneNo = command.getPhoneNo();

        phoneVerificationCodeService.sendVerificationCode(phoneNo);
    }

    @Transactional(rollbackFor = Exception.class)
    public LoggedInAccount login(@Valid AccountLoginCommand command) {
        String phoneNo = command.getPhoneNo();
        String verificationCode = command.getVerificationCode();

        return accountService.login(phoneNo, verificationCode);
    }

    public LoggedInAccount refreshLogin(@Valid AccountRefreshLoginCommand command) {
        AccountId accountId = AccountId.of(command.getAccountId());
        Account account = accountRepository.getByIdNotExistsThrowException(accountId);
        return account.login();
    }


    @Transactional(rollbackFor = Exception.class)
    public void disable(@Valid AccountDisableCommand command) {
        Account account = accountRepository.getByIdNotExistsThrowException(AccountId.of(command.getAccountId()));

        account.disable();

        accountRepository.save(account);
    }

    public void checkAvailable(@Valid AccountMediaAvailableCheckCommand command) {
        Account account = accountRepository.getByIdNotExistsThrowException(AccountId.of(command.getAccountId()));

        account.checkAvailable();
    }


    @Transactional(rollbackFor = Exception.class)
    public void update(@Valid AccountUpdateCommand command) {
        AccountId accountId = AccountId.of(command.getAccountId());
        String avatar = command.getAvatar();
        String name = command.getName();
        String password = command.getPassword();
        String introduction = command.getIntroduction();

        Account account = accountRepository.getByIdNotExistsThrowException(accountId);

        account.update(avatar, name, password,introduction);
        accountRepository.update(account);
    }

}
