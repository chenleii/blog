package com.chen.blog.core.sharedkernel.tracer;

import org.slf4j.LoggerFactory;

import java.lang.annotation.*;

/**
 * 链路跟踪监控日志输出
 * 作用于类或方法上
 * <p>
 * 优先使用方法上的注解字段
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
    String loggerName() default TracerConstant.DEFAULT_TRACE_MONITOR_LOGGER_NAME;
}
