package com.chen.blog.core.account.domain.model.service;

import com.chen.blog.core.account.port.SmsPort;
import com.chen.blog.core.sharedkernel.ddd.annotation.DomainService;
import lombok.extern.slf4j.Slf4j;

import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 21:51
 */
@Slf4j
@Named
@DomainService
public class PhoneVerificationCodeService {

    @Inject
    private SmsPort smsPort;

    /**
     * 发送验证码
     */
    public void sendVerificationCode(String phoneNo) {
        // 空实现
    }

    /**
     * 验证验证码
     *
     * @param phoneNo          手机号
     * @param verificationCode 验证码
     * @return 是/否
     */
    public boolean verifyVerificationCode(String phoneNo, String verificationCode) {
        // 默认返回验证成功
        return true;
    }
}
