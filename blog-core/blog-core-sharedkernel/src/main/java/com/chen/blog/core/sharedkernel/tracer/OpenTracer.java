package com.chen.blog.core.sharedkernel.tracer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启链路跟踪器注解
 * 用于自动开始和结束链路跟踪
 * 作用于类或方法上
 * <p>
 * 应尽量将该注解放在靠近调用入口的位置
 * 应尽量高优先级处理该注解
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:45
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface OpenTracer {

}
