package com.chen.blog.core.sharedkernel.event;

import com.chen.blog.core.sharedkernel.logger.Loggers;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Objects;

/**
 * 事件总线
 *
 * @author cl
 * @version 1.0
 * @since 2020/10/30
 */
@Named
public final class EventBus {

    private static EventPublisher eventPublisher;

    @Inject
    public void setEventPublisher(EventPublisher eventPublisher) {
        if (Objects.isNull(eventPublisher)) {
            throw new IllegalArgumentException("eventPublisher is null.");
        }
        EventBus.eventPublisher = eventPublisher;
    }

    public static void publish(DomainEvent event) {
        eventPublisher.publish(event);

        Loggers.EventBusLogger.log(event);
    }
}
