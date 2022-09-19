package com.chen.blog.interfaces.http.dto.input;

import com.chen.blog.interfaces.http.dto.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

/**
 * @author cl
 * @version 1.0
 * @since 2021/6/30 16:18
 */
@Getter
@Setter
@ToString
@Schema(description = "文章更新和发布输入数据")
public class ArticleUpdateAndPublishInputDTO implements DTO {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @Schema(description = "文章ID")
    private Long articleId;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;
    /**
     * 标签
     */
    @Schema(description = "标签")
    private Set<String> tags;
    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 是否发布
     */
    @Schema(description = "是否发布")
    private Boolean isPublish;

}
