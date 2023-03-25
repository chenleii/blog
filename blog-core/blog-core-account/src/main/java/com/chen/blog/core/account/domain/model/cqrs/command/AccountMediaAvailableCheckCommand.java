package com.chen.blog.core.account.domain.model.cqrs.command;

import com.chen.blog.core.sharedkernel.cqrs.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 21:43
 */
@Getter
@ToString
@Builder
public class AccountMediaAvailableCheckCommand implements Command {

    /**
     * 账户ID
     */
    @NotNull
    private final Long accountId;
}
