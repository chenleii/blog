package com.chen.blog.core.sharedkernel.cqrs.annotation;

import java.lang.annotation.*;

/**
 * 标记命令模型
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {
}
