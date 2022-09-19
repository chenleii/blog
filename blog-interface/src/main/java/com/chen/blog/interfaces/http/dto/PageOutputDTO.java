package com.chen.blog.interfaces.http.dto;

import com.chen.blog.core.sharedkernel.converter.SourceToTargetConverter;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.google.common.base.Preconditions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author cl
 * @version 1.0
 * @since 2021/6/30 16:17
 */
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "分页输出数据")
public class PageOutputDTO<T extends DTO> implements DTO {

    @Schema(description = "当前页数")
    private Long pageIndex;

    @Schema(description = "每页条数")
    private Long pageSize;

    @Schema(description = "总数")
    private Long total;

    @Schema(description = "数据列表")
    private List<T> list;



    @Schema(description = "分页查询的最后一条记录排序字段值")
    private List<Object> lastValues;


    public static <RESULT, T extends DTO> PageOutputDTO<T> of(Pagination<RESULT> pagination, SourceToTargetConverter<RESULT, T> mapper) {
        if (Objects.isNull(pagination)) {
            return PageOutputDTO.<T>builder()
                    .pageIndex(1L)
                    .pageSize(10L)
                    .total(0L)
                    .list(Collections.emptyList())
                    .lastValues(Collections.emptyList())
                    .build();
        }
        Preconditions.checkNotNull(mapper);

        return PageOutputDTO.<T>builder()
                .pageIndex(pagination.getPageIndex())
                .pageSize(pagination.getPageSize())
                .total(pagination.getTotal())
                .list(mapper.listToList(pagination.getList()))
                .lastValues(pagination.getLastValues())
                .build();
    }


    /**
     * 分页数据遍历
     *
     * @param action 映射方法
     * @return 映射后的分页对象
     */
    public PageOutputDTO<T> peek(Consumer<? super T> action) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(action);
        }
        return this;
    }
}
