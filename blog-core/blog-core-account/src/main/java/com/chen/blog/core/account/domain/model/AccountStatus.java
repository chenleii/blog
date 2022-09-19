package com.chen.blog.core.account.domain.model;

import com.chen.blog.core.sharedkernel.ddd.annotation.ValueObject;
import lombok.Getter;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 19:03
 */
@ValueObject
@Getter
public enum AccountStatus {

    ENABLED(true),

    DISABLED(false),
    ;

    /**
     * 是否可用的
     */
    private final boolean isAvailable;

    AccountStatus(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
