package com.chen.blog.core.sharedkernel.trace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.inject.Named;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:45
 */
@Named
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceAspectj {


    @Around("@within(com.chen.blog.core.sharedkernel.trace.Trace) " +
            "|| @annotation(com.chen.blog.core.sharedkernel.trace.Trace)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Traces.startTrace();
            return proceedingJoinPoint.proceed();
        } finally {
            Traces.endTrace();
        }
    }
}
