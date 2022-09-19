package com.chen.blog.core.sharedkernel.event;

import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * 领域事件
 *
 * @author cl
 * @version 1.0
 * @since 2020/11/24 11:27
 */
@SuperBuilder
@com.chen.blog.core.sharedkernel.event.annotation.DomainEvent
public abstract class DomainEvent {

    /**
     * 发生于
     */
    private final Instant occurredAt = Instant.now();


    /**
     * 事件标识
     * <p>
     * 如果可以，应该重写该方法保证唯一。
     *
     * @return 标识
     */
    public String code() {
        return this.getClass().getName();
    }

    /**
     * 事件类型
     *
     * @return 类型
     */
    public String type() {
        return this.getClass().getSimpleName();
    }

    /**
     * 事件发生时间
     *
     * @return 时间
     */
    public Instant occurredAt() {
        return this.occurredAt;
    }
}
