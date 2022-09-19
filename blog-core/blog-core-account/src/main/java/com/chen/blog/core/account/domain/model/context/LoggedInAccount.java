package com.chen.blog.core.account.domain.model.context;

import com.chen.blog.core.account.domain.model.AccountStatus;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 22:05
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoggedInAccount implements Serializable {

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
    /**
     * 登录于
     */
    private Instant loggedInAt;
}
