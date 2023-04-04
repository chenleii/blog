package com.chen.blog.core.sharedkernel.tracer;

import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;

import jakarta.inject.Named;

import java.util.Objects;
import java.util.Optional;

/**
 * 链路跟踪监控日志输出注解的AOP处理
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:45
 */
@Named
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class TraceMonitorLogAspectj {


    @Around("@within(com.chen.blog.core.sharedkernel.tracer.TraceMonitorLog) " +
            "|| @annotation(com.chen.blog.core.sharedkernel.tracer.TraceMonitorLog)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        TraceMonitorLog traceMonitorLog = resolveTraceMonitorLog(proceedingJoinPoint);
        if (Objects.isNull(traceMonitorLog)) {
            // 不应该出现该情况，那也加个判断，直接返回。
            return proceedingJoinPoint.proceed();
        }

        Throwable throwable = null;
        long startTime = System.currentTimeMillis();
        try {

            return proceedingJoinPoint.proceed();
        } catch (Throwable t) {
            throwable = t;
            throw throwable;
        } finally {

            long endTime = System.currentTimeMillis();
            Logger logger = LoggerFactory.getLogger(traceMonitorLog.loggerName());
            logger.info("tags={},ref={},tid={},time={},rt={},error={}",
                    traceMonitorLog.tags(),
                    proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName() + "#" + proceedingJoinPoint.getSignature().getName(),
                    Tracers.getTraceId(),
                    startTime,
                    endTime - startTime,
                    Optional.ofNullable(throwable).map(Throwable::getClass).map(Class::getSimpleName).orElse(null));

        }
    }

    private TraceMonitorLog resolveTraceMonitorLog(ProceedingJoinPoint proceedingJoinPoint) {
        TraceMonitorLog classTraceMonitorLog = Optional.ofNullable(proceedingJoinPoint.getTarget())
                .map(Object::getClass)
                .map((clazz) -> AnnotationUtils.findAnnotation(clazz, TraceMonitorLog.class))
                .orElse(null);
        TraceMonitorLog methodTraceMonitorLog = Optional.ofNullable(proceedingJoinPoint.getSignature())
                .filter(MethodSignature.class::isInstance)
                .map(MethodSignature.class::cast)
                .map(MethodSignature::getMethod)
                .map((method -> AnnotationUtils.findAnnotation(method, TraceMonitorLog.class)))
                .orElse(null);
        return ObjectUtils.defaultIfNull(methodTraceMonitorLog, classTraceMonitorLog);
    }

}
