package com.chen.blog.infrastructure.persistence.repository;

import com.chen.blog.core.account.domain.model.Account;
import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.account.domain.model.cqrs.query.AccountQuery;
import com.chen.blog.core.account.domain.model.cqrs.representation.AccountRepresentation;
import com.chen.blog.core.account.domain.model.repository.AccountQueryRepository;
import com.chen.blog.core.account.domain.model.repository.AccountRepository;
import com.chen.blog.infrastructure.persistence.repository.dataobject.AccountDO;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.AccountConverter;
import com.chen.blog.infrastructure.persistence.repository.mongodb.AccountMongoRepository;
import com.chen.blog.infrastructure.persistence.repository.representationconverter.AccountRepresentationConverter;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Objects;
import java.util.Optional;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/7 16:41
 */
@Named
@Slf4j
public class AccountRepositoryImpl implements AccountRepository, AccountQueryRepository {

    @Inject
    private MongoTemplate mongoTemplate;

    @Inject
    private AccountMongoRepository accountMongoRepository;

    @Override
    public Account getById(AccountId accountId) {
        Preconditions.checkNotNull(accountId);

        Optional<AccountDO> accountDO = accountMongoRepository.findById(accountId.getId());
        return AccountConverter.MAPPER.from(accountDO.orElse(null));
    }

    @Override
    public Account getByPhoneNo(String phoneNo) {
        Preconditions.checkNotNull(phoneNo);

        Optional<AccountDO> accountDO = accountMongoRepository.findByPhoneNo(phoneNo);
        return AccountConverter.MAPPER.from(accountDO.orElse(null));
    }

    @Override
    public void save(Account account) {
        Preconditions.checkNotNull(account);

        AccountDO accountDO = AccountConverter.MAPPER.to(account);
        accountMongoRepository.save(accountDO);
    }

    @Override
    public void update(Account account) {
        save(account);

    }

    @Override
    public AccountRepresentation queryById(AccountQuery query) {
        Preconditions.checkNotNull(query);
        Long accountId = query.getAccountId();
        if (Objects.isNull(accountId)) {
            return null;
        }

        Optional<AccountDO> accountDO = accountMongoRepository.findById(query.getAccountId());
        return accountDO
                .map(AccountRepresentationConverter.MAPPER::from)
                .orElse(null);
    }
}
