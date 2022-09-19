package com.chen.blog.core.sharedkernel.trace;

import org.slf4j.MDC;

import java.util.Objects;
import java.util.UUID;

/**
 * 链路追踪
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:33
 */
public final class Traces {
    /**
     * 链路追踪ID的KEY
     */
    public static final String TID_KEY = "tid";

    /**
     * 获取链路跟踪ID
     *
     * @return 链路跟踪ID
     */
    public static String getTraceId() {
        return MDC.get(TID_KEY);
    }

    /**
     * 是否已开始链路追踪
     *
     * @return 是/否
     */
    public static boolean isAlreadyTrace() {
        return Objects.nonNull(MDC.get(TID_KEY));
    }

    /**
     * 开始链路跟踪
     */
    public static void startTrace() {
        if (isAlreadyTrace()) {
            return;
        }
        MDC.put(TID_KEY, UUID.randomUUID().toString());
    }

    /**
     * 结束链路追踪
     */
    public static void endTrace() {
        MDC.remove(TID_KEY);
    }

}
