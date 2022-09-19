package com.chen.blog.core.sharedkernel.cqrs;

/**
 * 查询仓储
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/10 12:58
 */
@com.chen.blog.core.sharedkernel.cqrs.annotation.QueryRepository
public interface QueryRepository<T extends Representation> {
}
