package com.chen.blog.core.sharedkernel.tracer;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.MDC;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 链路跟踪器
 * <p>
 * 仅在当前线程处理，无线程安全问题。
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:33
 */
public final class Tracers {

    /**
     * 链路跟踪器的标识
     * <p>
     * 用于记录开始链路跟踪方法${@link Tracers#startTrace(Object)}的mark参数值
     * 用来保证{@link Tracers#startTrace(Object)}和{@link Tracers#endTrace(Object)}方法之间的幂等。
     */
    private static final ThreadLocal<AtomicReference<Object>> START_TRACE_MARK_THREAD_LOCAL = ThreadLocal.withInitial(AtomicReference::new);

    /**
     * 获取链路跟踪ID
     *
     * @return 链路跟踪ID
     */
    public static String getTraceId() {
        return MDC.get(TracerConstant.LOG_TID_KEY);
    }

    /**
     * 开始链路跟踪
     *
     * @param mark 开始链路跟踪的标识
     */
    public static void startTrace(Object mark) {
        startTrace(mark, null);
    }

    /**
     * 开始链路跟踪
     *
     * @param mark 开始链路跟踪的标识
     * @param tid  自定义的链路跟踪ID（也可以是前一个调用方传递的链路跟踪ID）
     *             为null默认使用UUID
     */
    public static void startTrace(Object mark, String tid) {
        Preconditions.checkNotNull(mark);

        // 判断是否已开始链路跟踪，并设置标识。
        if (!START_TRACE_MARK_THREAD_LOCAL.get().compareAndSet(null, mark)) {
            // 已经开始链路跟踪直接返回
            return;
        }

        tid = ObjectUtils.defaultIfNull(tid, UUID.randomUUID().toString().replace("-", ""));
        MDC.put(TracerConstant.LOG_TID_KEY, tid);
    }

    /**
     * 结束链路跟踪
     *
     * @param mark 开始链路跟踪的标识
     */
    public static void endTrace(Object mark) {
        Preconditions.checkNotNull(mark);

        final Object currentMark = START_TRACE_MARK_THREAD_LOCAL.get().getAcquire();
        if (Objects.isNull(currentMark)) {
            // 未开始链路跟踪直接返回
            return;
        }

        // 开始链路跟踪的标识，才能结束链路跟踪。
        if (!Objects.equals(currentMark, mark)) {
            return;
        }

        MDC.remove(TracerConstant.LOG_TID_KEY);
        START_TRACE_MARK_THREAD_LOCAL.remove();
    }

}
