package com.chen.blog.core.sharedkernel.ddd.annotation;

import javax.inject.Named;
import java.lang.annotation.*;

/**
 * 标记领域工厂
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/10 12:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Named
public @interface DomainFactory {

}
