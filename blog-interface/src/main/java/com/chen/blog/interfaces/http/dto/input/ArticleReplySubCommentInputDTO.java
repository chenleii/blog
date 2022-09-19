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
@Schema(description = "文章回复子评论输入数据")
public class ArticleReplySubCommentInputDTO implements DTO {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @Schema(description = "文章ID")
    private Long articleId;

    /**
     * 评论ID
     */
    @Schema(description = "评论ID")
    private Long commentId;

    /**
     * 回复的评论ID
     */
    @Schema(description = "回复的子评论ID")
    private Long replyCommentId;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    private String content;


}
