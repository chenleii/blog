package com.chen.blog.core.sharedkernel.validator;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Validator;

/**
 * @author cl
 * @version 1.0
 * @since 2021/7/13 19:51
 */
@Named
public final class Validators {

    /**
     * 验证器
     */
    private static Validator validator;

    @Inject
    public Validators(Validator validator) {
        Validators.validator = validator;
    }


    public static Validator current() {
        return validator;
    }
}
