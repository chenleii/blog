package com.chen.blog.core.sharedkernel.trace;

import org.slf4j.LoggerFactory;

import java.lang.annotation.*;

/**
 * 作用于类或方法上
 * 打印链路追踪监控日志
 * <p>
 * 优先使用方法上的注解
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:45
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface TraceMonitorLog {

    /**
     * 标签
     * <p>
     * 用于区分自定义特殊场景监控
     */
    String tags() default "";

    /**
     * 日志名称
     * <p>
     * 使用{@link LoggerFactory#getLogger(java.lang.String)}获取日志
     *
     * @return 日志名称
     */
    String loggerName() default TraceConstant.DEFAULT_TRACE_MONITOR_LOGGER_NAME;
}
