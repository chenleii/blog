package com.chen.blog.core.sharedkernel.ddd.annotation;

import java.lang.annotation.*;

/**
 * 标记聚合根
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/10 12:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Entity
public @interface AggregateRoot {

}
