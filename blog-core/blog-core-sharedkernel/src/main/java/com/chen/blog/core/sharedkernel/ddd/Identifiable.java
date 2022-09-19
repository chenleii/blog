package com.chen.blog.core.sharedkernel.ddd;


/**
 * 可识别的类型，返回{@link Identifier}。
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/10 12:58
 */
public interface Identifiable<ID> {

    ID getId();
}
