package com.chen.blog.core.sharedkernel.cqrs;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cl
 * @version 1.0
 * @since 2020/11/5
 */
@SuperBuilder
@Getter
public class PageQuery implements Query {
    /**
     * 当前页数
     */
    @NotNull(message = "当前页数不能为空")
    private final Long pageIndex;
    /**
     * 每页条数
     */
    @NotNull(message = "每页大小不能为空")
    private final Long pageSize;


    /**
     * 分页查询的最后一条记录排序字段值
     * <p>
     * 按照排序字段顺序
     * <p>
     * 第一页无需传
     * <p>
     * 主要用于分页优化
     * 应优先使用该字段分页
     * 在查询和排序条件无变化时使用
     * 一般使用分页查询返回的{@link com.chen.blog.core.sharedkernel.cqrs.Pagination#lastPageValues}
     */
    private final List<Object> lastValues;
}
