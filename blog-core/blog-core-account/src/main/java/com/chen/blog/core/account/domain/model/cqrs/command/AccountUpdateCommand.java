package com.chen.blog.core.account.domain.model.cqrs.command;

import com.chen.blog.core.sharedkernel.cqrs.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 21:43
 */
@Getter
@ToString
@Builder
public class AccountUpdateCommand implements Command {

    /**
     * id
     */
    @NotNull
    private final Long accountId;

    /**
     * 头像
     */
    @NotBlank
    private final String avatar;
    /**
     * 名称
     */
    @NotBlank
    private final String name;
    /**
     * 密码
     */
    private final String password;
    /**
     * 简介
     */
    private final String introduction;
}
