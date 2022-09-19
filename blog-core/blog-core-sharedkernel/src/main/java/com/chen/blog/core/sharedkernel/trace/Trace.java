package com.chen.blog.core.sharedkernel.trace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作用于类或方法上
 * 用于自动开始和结束链路追踪
 * <p>
 * 应将处理该注解的优先级尽可能的提高
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:45
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Trace {

}
