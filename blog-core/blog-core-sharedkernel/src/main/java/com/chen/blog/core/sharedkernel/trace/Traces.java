package com.chen.blog.core.sharedkernel.trace;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.MDC;

import java.util.Objects;
import java.util.UUID;

/**
 * 链路追踪
 * <p>
 * 仅在当前线程处理，无线程安全问题。
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
     * 用于记录已开始链路追踪标识
     * <p>
     * 用来保证{@link Traces#startTrace(Object)}和{@link Traces#endTrace(Object)}幂等。
     */
    private static final ThreadLocal<Object> OBJECT_THREAD_LOCAL = new InheritableThreadLocal<>();

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
        return Objects.nonNull(OBJECT_THREAD_LOCAL.get());
    }

    /**
     * 开始链路跟踪
     *
     * @param object 开始链路追踪的标识
     */
    public static void startTrace(Object object) {
        startTrace(object, null);
    }

    /**
     * 开始链路跟踪
     *
     * @param object 开始链路追踪的标识
     * @param tid    自定义的链路追踪ID（也可以是前一个调用方传递的链路追踪ID）
     *               为null默认使用UUID
     */
    public static void startTrace(Object object, String tid) {
        Preconditions.checkNotNull(object);

        tid = ObjectUtils.defaultIfNull(tid, UUID.randomUUID().toString());

        if (isAlreadyTrace()) {
            // 已经开始链路追踪直接返回
            return;
        }
        // 设置标识
        OBJECT_THREAD_LOCAL.set(object);

        MDC.put(TID_KEY, tid);
    }

    /**
     * 结束链路追踪
     *
     * @param object 开始链路追踪的标识
     */
    public static void endTrace(Object object) {
        Preconditions.checkNotNull(object);

        if (!isAlreadyTrace()) {
            // 未开始链路追踪直接返回
            return;
        }

        // 开始链路追踪的标识，才能结束。
        Object currentObject = OBJECT_THREAD_LOCAL.get();
        if (!Objects.equals(currentObject, object)) {
            return;
        }

        MDC.remove(TID_KEY);
        OBJECT_THREAD_LOCAL.remove();
    }

}
