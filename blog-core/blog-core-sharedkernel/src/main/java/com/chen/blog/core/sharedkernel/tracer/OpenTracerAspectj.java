package com.chen.blog.core.sharedkernel.tracer;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import jakarta.inject.Named;

/**
 * 开启链路跟踪器注解的AOP处理
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:45
 */
@Named
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OpenTracerAspectj {

    @Around("@within(com.chen.blog.core.sharedkernel.tracer.OpenTracer) " +
            "|| @annotation(com.chen.blog.core.sharedkernel.tracer.OpenTracer)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        try {
            Tracers.startTrace(proceedingJoinPoint.getThis());
            return proceedingJoinPoint.proceed();
        } finally {
            Tracers.endTrace(proceedingJoinPoint.getThis());
        }
    }
}
