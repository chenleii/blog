package com.chen.blog.core.sharedkernel.ddd;

/**
 * 聚合根
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/10 12:58
 */
@com.chen.blog.core.sharedkernel.ddd.annotation.AggregateRoot
public interface AggregateRoot<T extends AggregateRoot<T, ID>, ID extends Identifier> extends Entity<T, ID> {

}
