package com.chen.blog.core.account.domain.model.repository;

import com.chen.blog.core.account.domain.model.Account;
import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.account.domain.model.exception.AccountNotExistsException;
import com.chen.blog.core.sharedkernel.ddd.annotation.Repository;

import java.util.Optional;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 21:46
 */
@Repository
public interface AccountRepository {

    Account getById(AccountId accountId);


    default Account getByIdNotExistsThrowException(AccountId accountId) {
        Account account = getById(accountId);
        return Optional.ofNullable(account)
                .orElseThrow(() -> new AccountNotExistsException("account not exists. " +
                        "accountId:[" + accountId.getId() + "]."));
    }

    Account getByPhoneNo(String phoneNo);

    void save(Account account);

    void update(Account account);
}
