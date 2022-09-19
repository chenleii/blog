package com.chen.blog.core.sharedkernel.cqrs;

import com.google.common.base.Preconditions;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author cl
 * @version 1.0
 * @since 2020/11/5
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Pagination<T> implements Representation {

    /**
     * 当前页数
     */
    private Long pageIndex;
    /**
     * 每页条数
     */
    private Long pageSize;

    /**
     * 总数
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<T> list;


    /**
     * 分页查询的最后一条记录排序字段值
     * <p>
     * 按照排序字段顺序
     */
    private List<Object> lastValues;

    public Pagination(Long pageIndex, Long pageSize, Long total, List<T> list, List<Object> lastValues) {
        Preconditions.checkNotNull(pageIndex);
        Preconditions.checkNotNull(pageSize);
        Preconditions.checkNotNull(total);

        list = ObjectUtils.defaultIfNull(list, Collections.emptyList());
        list = Collections.unmodifiableList(list);

        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
        this.lastValues = lastValues;
    }

    public static <T> Pagination<T> create(long pageIndex, long pageSize) {
        return new Pagination<>(pageIndex, pageSize, 0L, Collections.emptyList(), null);
    }

    public static <T> Pagination<T> create(PageQuery pageQuery) {
        return new Pagination<>(pageQuery.getPageIndex(), pageQuery.getPageSize(), 0L, Collections.emptyList(), null);
    }


    public static <T> Pagination<T> create(long pageIndex, long pageSize,
                                           long total, List<T> list) {
        return new Pagination<>(pageIndex, pageSize, total, list, null);
    }

    public static <T> Pagination<T> create(PageQuery pageQuery,
                                           long total, List<T> list) {
        return new Pagination<>(pageQuery.getPageIndex(), pageQuery.getPageSize(), total, list, null);
    }

    public static <T> Pagination<T> create(long pageIndex, long pageSize,
                                           long total, List<T> list, List<Object> lastPageValues) {
        return new Pagination<>(pageIndex, pageSize, total, list, lastPageValues);
    }

    public static <T> Pagination<T> create(PageQuery pageQuery,
                                           long total, List<T> list, List<Object> lastPageValues) {
        return new Pagination<>(pageQuery.getPageIndex(), pageQuery.getPageSize(), total, list, lastPageValues);
    }


    /**
     * 分页数据映射为指定类型
     *
     * @param mapper 映射方法
     * @param <R>    映射后的类型
     * @return 映射后的分页对象
     */
    public <R> Pagination<R> map(Function<List<T>, List<R>> mapper) {
        return Pagination.create(this.getPageIndex(), this.getPageSize(),
                this.getTotal(), mapper.apply(this.list),this.lastValues);
    }

    /**
     * 分页数据窥探
     *
     * @param action 处理方法
     * @return 处理后的分页对象
     */
    public Pagination<T> peek(Consumer<? super T> action) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(action);
        }
        return this;
    }
}
