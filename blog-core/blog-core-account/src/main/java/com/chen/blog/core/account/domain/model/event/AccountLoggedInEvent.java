package com.chen.blog.core.account.domain.model.event;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/10 0:22
 */
@SuperBuilder
@Getter
@ToString(callSuper = true)
public class AccountLoggedInEvent extends AbstractAccountEvent{
    /**
     * 登录于
     */
    private Instant loggedInAt;
}
