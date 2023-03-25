package com.chen.blog.core.account.domain.model.service;

import com.chen.blog.core.account.domain.model.Account;
import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.account.domain.model.context.LoggedInAccount;
import com.chen.blog.core.account.domain.model.exception.AccountLoginFailedException;
import com.chen.blog.core.account.domain.model.repository.AccountRepository;
import com.chen.blog.core.sharedkernel.ddd.annotation.DomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Objects;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 21:51
 */
@DomainService
@Slf4j
@Named
public class AccountService {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private PhoneVerificationCodeService phoneVerificationCodeService;

    @Transactional(rollbackFor = Exception.class)
    public LoggedInAccount login(String phoneNo, String verificationCode) {
        boolean verifyVerificationCode = phoneVerificationCodeService.verifyVerificationCode(phoneNo, verificationCode);
        if (!verifyVerificationCode) {
            throw new AccountLoginFailedException("account Login verify Verification Code failed.");
        }

        Account account = accountRepository.getByPhoneNo(phoneNo);
        if (Objects.nonNull(account)) {
            return account.login();
        }

        AccountId accountId = AccountId.generateId();
        account = Account.create(accountId, phoneNo);
        accountRepository.save(account);
        return account.login();
    }
}
