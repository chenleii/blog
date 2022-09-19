package com.chen.blog.core.sharedkernel.event.annotation;

import java.lang.annotation.*;

/**
 * 标记领域事件
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DomainEvent {
}
