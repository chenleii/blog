package com.chen.blog.core.account.domain.model.cqrs.command;

import com.chen.blog.core.sharedkernel.cqrs.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 21:43
 */
@Getter
@ToString
@Builder
public class AccountLoginCommand implements Command {

    /**
     * 手机号
     */
    @NotBlank
    private final String phoneNo;

    /**
     * 验证码
     */
    @NotBlank
    private final String verificationCode;
}
