package com.chen.blog.core.sharedkernel.ddd.annotation;

import java.lang.annotation.*;

/**
 * 标记限界上下文
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/10 12:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PACKAGE, ElementType.ANNOTATION_TYPE})
@Documented
public @interface BoundedContext {


    String id() default "";


    String name() default "";

    String description() default "";
}
