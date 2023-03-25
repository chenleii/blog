package com.chen.blog.core.hot.doamin.model.cqrs.command;

import com.chen.blog.core.sharedkernel.cqrs.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import jakarta.validation.constraints.NotBlank;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:09
 */
@Getter
@ToString
@Builder
public class ArticleSearchCommand implements Command {

    /**
     * 账户ID
     */
    @NotBlank
    private final String keywords;


}
