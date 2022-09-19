package com.chen.blog.infrastructure.event;

import com.chen.blog.core.sharedkernel.event.DomainEvent;
import com.chen.blog.core.sharedkernel.event.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author cl
 * @version 1.0
 * @since 2020/11/27 16:25
 */
@Named
public class SpringEventPublisher implements EventPublisher {

    @Inject
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
