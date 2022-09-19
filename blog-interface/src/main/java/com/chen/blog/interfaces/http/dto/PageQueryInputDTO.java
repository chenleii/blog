package com.chen.blog.interfaces.http.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springdoc.api.annotations.ParameterObject;

import java.util.List;

/**
 * @author cl
 * @version 1.0
 * @since 2021/6/30 16:17
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description ="分页传入数据")
@ParameterObject
public class PageQueryInputDTO implements DTO {

    @Schema(description = "当前页数")
    @Parameter(description = "当前页数")
    private Long pageIndex;

    @Schema(description = "每页条数")
    @Parameter(description = "每页条数")
    private Long pageSize;

    @Schema(description = "分页查询的最后一条记录排序字段值")
    @Parameter(description = "分页查询的最后一条记录排序字段值")
    private List<Object> lastValues;
}
