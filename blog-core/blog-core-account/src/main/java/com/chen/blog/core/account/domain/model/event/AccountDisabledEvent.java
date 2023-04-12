package com.chen.blog.core.account.domain.model.event;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * @author cl
 * @version 1.0
 * @since 2023/4/12 21:32
 */
@SuperBuilder
@Getter
@ToString(callSuper = true)
public class AccountDisabledEvent extends AbstractAccountEvent {
    /**
     * 禁用于
     */
    private Instant disabledAt;
}
