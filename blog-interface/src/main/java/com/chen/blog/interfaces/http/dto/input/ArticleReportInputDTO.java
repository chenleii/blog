package com.chen.blog.interfaces.http.dto.input;

import com.chen.blog.interfaces.http.dto.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author cl
 * @version 1.0
 * @since 2021/6/30 16:18
 */
@Getter
@Setter
@ToString
@Schema(description = "文章举报输入数据")
public class ArticleReportInputDTO implements DTO {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @Schema(description = "文章ID")
    private Long articleId;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;


}
