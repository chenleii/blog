package com.chen.blog.core.sharedkernel.ddd.annotation;

import java.lang.annotation.*;

/**
 * 标记外部交互的端口
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/10 12:58
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Documented
public @interface Port {
}
