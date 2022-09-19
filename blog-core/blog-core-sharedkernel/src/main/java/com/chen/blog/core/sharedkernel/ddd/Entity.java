package com.chen.blog.core.sharedkernel.ddd;

/**
 * 实体
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/10 12:58
 */
@com.chen.blog.core.sharedkernel.ddd.annotation.Entity
public interface Entity<T extends AggregateRoot<T, ?>, ID> extends Identifiable<ID> {}
