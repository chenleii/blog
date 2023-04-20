package com.chen.blog.core.account.domain.model.repository;

import com.chen.blog.core.account.domain.model.cqrs.query.AccountQuery;
import com.chen.blog.core.account.domain.model.cqrs.representation.AccountRepresentation;
import com.chen.blog.core.sharedkernel.cqrs.annotation.QueryRepository;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 21:46
 */
@QueryRepository
public interface AccountQueryRepository {
    AccountRepresentation queryById(AccountQuery query);
}
