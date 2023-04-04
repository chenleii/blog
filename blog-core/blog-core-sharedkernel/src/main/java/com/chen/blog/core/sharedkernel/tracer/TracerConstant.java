package com.chen.blog.core.sharedkernel.tracer;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/16 21:50
 */
public final class TracerConstant {
    /**
     * 日志中链路跟踪ID的KEY
     */
    public static final String LOG_TID_KEY = "tid";

    /**
     * 默认链路跟踪监控日志名称
     */
    public static final String DEFAULT_TRACE_MONITOR_LOGGER_NAME = "TraceMonitorLog";

    /**
     * 默认http链路跟踪监控日志名称
     */
    public static final String DEFAULT_HTTP_TRACE_MONITOR_LOGGER_NAME = "HttpTraceMonitorLog";

    /**
     * HTTP的链路跟踪ID的HEADER名称
     */
    public static final String HTTP_TRACE_ID_HEADER_NAME = "x-tid";
}
