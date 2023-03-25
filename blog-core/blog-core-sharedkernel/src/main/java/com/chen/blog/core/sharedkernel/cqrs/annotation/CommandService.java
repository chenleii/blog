package com.chen.blog.core.sharedkernel.cqrs.annotation;

import com.chen.blog.core.sharedkernel.ddd.annotation.ApplicationService;
import com.chen.blog.core.sharedkernel.trace.TraceMonitorLog;

import jakarta.inject.Named;
import java.lang.annotation.*;

/**
 * 标记命令服务
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/10 12:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Named
@ApplicationService
public @interface CommandService {

}
