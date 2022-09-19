package com.chen.blog.core.article.domain.model.cqrs.command;

import com.chen.blog.core.sharedkernel.cqrs.Command;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:09
 */
@Getter
@ToString(callSuper = true)
@SuperBuilder
public class ArticleUpdateAndPublishCommand implements Command {

    /**
     * 文章ID
     */
    @NotNull
    private Long articleId;


    /**
     * 账户ID
     */
    @NotNull
    private final Long accountId;

    /**
     * 标题
     */
    @NotBlank
    private final String title;
    /**
     * 标签
     */
    private final Set<String> tags;
    /**
     * 内容
     */
    @NotBlank
    private final String content;

    /**
     * 是否发布
     * <p>
     * 如果已经发布了，该参数无效。
     */
    @NotNull
    private final Boolean isPublish;

}
