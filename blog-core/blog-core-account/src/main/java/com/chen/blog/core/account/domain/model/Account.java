package com.chen.blog.core.account.domain.model;

import com.chen.blog.core.account.domain.model.context.LoggedInAccount;
import com.chen.blog.core.account.domain.model.event.AccountDisabledEvent;
import com.chen.blog.core.account.domain.model.event.AccountLoggedInEvent;
import com.chen.blog.core.account.domain.model.exception.AccountNotAvailableException;
import com.chen.blog.core.sharedkernel.ddd.annotation.AggregateRoot;
import com.chen.blog.core.sharedkernel.event.DomainEventPublisher;
import lombok.*;

import java.time.Instant;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/6 15:27
 */
@AggregateRoot
@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public class Account implements DomainEventPublisher {
    /**
     * id
     */
    private AccountId id;

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


    public static Account create(AccountId id, String phoneNo) {
        Instant now = Instant.now();
        Account account = Account.builder()
                .id(id)
                .avatar("")
                .name(phoneNo)
                .phoneNo(phoneNo)
                .password("")
                .introduction("")
                .influenceValue(0)
                .status(AccountStatus.ENABLED)
                .createdAt(now)
                .updatedAt(now)
                .build();
        return account;
    }


    /**
     * 登录
     *
     * @return 登录的账户
     */
    public LoggedInAccount login() {
        LoggedInAccount loggedInAccount = LoggedInAccount.builder()
                .id(getId().getId())
                .avatar(getAvatar())
                .name(getName())
                .phoneNo(getPhoneNo())
                .introduction(getIntroduction())
                .influenceValue(getInfluenceValue())
                .status(getStatus())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .loggedInAt(Instant.now())
                .build();

        publishEvent(
                AccountLoggedInEvent.builder()
                        .id(getId().getId())
                        .avatar(getAvatar())
                        .name(getName())
                        .phoneNo(getPhoneNo())
                        .introduction(getIntroduction())
                        .influenceValue(getInfluenceValue())
                        .status(getStatus())
                        .createdAt(getCreatedAt())
                        .updatedAt(getUpdatedAt())
                        .loggedInAt(Instant.now())
                        .build()
        );

        return loggedInAccount;
    }


    public boolean isAvailable() {
        return getStatus().isAvailable();
    }

    public void checkAvailable() {
        if (!isAvailable()) {
            throw new AccountNotAvailableException("account not available.");
        }
    }

    public void disable() {
        setStatus(AccountStatus.DISABLED);

        publishEvent(
                AccountDisabledEvent.builder()
                        .id(getId().getId())
                        .avatar(getAvatar())
                        .name(getName())
                        .phoneNo(getPhoneNo())
                        .introduction(getIntroduction())
                        .influenceValue(getInfluenceValue())
                        .status(getStatus())
                        .createdAt(getCreatedAt())
                        .updatedAt(getUpdatedAt())

                        .disabledAt(Instant.now())
                        .build()
        );
    }

    public void update(String avatar, String name, String password, String introduction) {
        checkAvailable();

        setAvatar(avatar);
        setName(name);
        setPassword(password);
        setIntroduction(introduction);

        setUpdatedAt(Instant.now());
    }


    public void addInfluenceValue(Integer value) {
        checkAvailable();

        setInfluenceValue(getInfluenceValue() + value);

    }


}
