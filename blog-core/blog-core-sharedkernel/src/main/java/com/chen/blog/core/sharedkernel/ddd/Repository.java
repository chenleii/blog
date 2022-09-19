package com.chen.blog.core.sharedkernel.ddd;

/**
 * 仓储
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/10 12:58
 */
@com.chen.blog.core.sharedkernel.ddd.annotation.Repository
public interface Repository<T extends AggregateRoot<T, ID>, ID extends Identifier> {
}
