package com.chen.blog.core.account.domain.model.cqrs.query;

import com.chen.blog.core.sharedkernel.cqrs.Query;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 17:58
 */
@Getter
@ToString
@Builder
public class AccountQuery implements Query {


    /**
     * 账户ID
     */
    @NotNull
    private final Long accountId;
}
