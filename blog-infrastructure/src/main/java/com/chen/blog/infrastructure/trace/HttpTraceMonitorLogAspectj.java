package com.chen.blog.infrastructure.trace;

import com.chen.blog.core.sharedkernel.trace.TraceConstant;
import com.chen.blog.core.sharedkernel.trace.Traces;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:45
 */
@Named
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 150)
public class HttpTraceMonitorLogAspectj {

    private final Logger logger = LoggerFactory.getLogger(TraceConstant.DEFAULT_HTTP_TRACE_MONITOR_LOGGER_NAME);


    @Around("@within(org.springframework.stereotype.Controller) " +
            "|| @within(org.springframework.web.bind.annotation.RestController) " +
            "|| @within(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)"+
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping)"+
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)"+
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping)"+
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)"+
            "|| @annotation(org.springframework.web.bind.annotation.PatchMapping)"
    )
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = resolveRequest();
        if (Objects.isNull(request)) {
            // 不是servlet上下文，直接返回。
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
            logger.info("uri={},ref={},tid={},time={},rt={},error={}",
                    request.getRequestURI(),
                    proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName() + "#" + proceedingJoinPoint.getSignature().getName(),
                    Traces.getTraceId(),
                    startTime,
                    endTime - startTime,
                    Optional.ofNullable(throwable).map(Throwable::getClass).map(Class::getSimpleName).orElse(null));

        }
    }

    private HttpServletRequest resolveRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .orElse(null);
    }
}
