package com.chen.blog.core.sharedkernel.event;

/**
 * 事件发布器
 *
 * @author cl
 * @version 1.0
 * @since 2020/10/30
 */
public interface EventPublisher {

    /**
     * 发布领域事件
     *
     * @param event 事件
     */
    void publish(DomainEvent event);
}

