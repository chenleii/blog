package com.chen.blog.core.account.domain.model.event;

import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.account.domain.model.AccountStatus;
import com.chen.blog.core.sharedkernel.event.DomainEvent;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 21:42
 */
@SuperBuilder
@Getter
@ToString
public class AbstractAccountEvent extends DomainEvent {

    /**
     * id
     */
    private Long id;

    /**
     * 头像
     */
    private String avatar;
    /**
     * 名称
     */
    private String name;
    /**
     * 手机号
     */
    private String phoneNo;
    /**
     * 密码
     */
    private String password;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 影响力
     */
    private Integer influenceValue;

    /**
     * 状态
     */
    private AccountStatus status;

    /**
     * 创建于
     */
    private Instant createdAt;
    /**
     * 更新于
     */
    private Instant updatedAt;
}
