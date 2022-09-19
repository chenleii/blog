package com.chen.blog.core.sharedkernel.specification;

import com.chen.blog.core.sharedkernel.exception.DomainRuntimeException;

/**
 * @author cl
 * @version 1.0
 * @since 2020/10/29
 */
public class SpecificationUnsatisfiedException extends DomainRuntimeException {

    public SpecificationUnsatisfiedException(String errorMsg) {
        super(errorMsg);
    }

}
