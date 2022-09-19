package com.chen.blog.core.account.domain.model.cqrs.representation;

import com.chen.blog.core.account.domain.model.AccountStatus;
import com.chen.blog.core.sharedkernel.cqrs.Representation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 14:51
 */
@Getter
@Setter
@ToString
public class AccountRepresentation implements Representation {


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
