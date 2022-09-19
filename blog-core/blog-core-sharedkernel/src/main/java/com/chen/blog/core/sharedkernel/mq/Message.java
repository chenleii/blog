package com.chen.blog.core.sharedkernel.mq;

import java.time.Instant;

/**
 * @author cl
 * @version 1.0
 * @since 2021/7/22 12:15
 */
public abstract class Message {

    /**
     * 发生于
     */
    private final Instant occurredAt = Instant.now();

    /**
     * 消息的key
     * <p>
     * 需要尽量保持唯一
     *
     * @return key
     */
    public String key() {
        return null;
    }


    /**
     * 发生时间
     *
     * @return 时间
     */
    public Instant occurredAt() {
        return this.occurredAt;
    }
}
