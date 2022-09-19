package com.chen.blog.core.article.domain.model.event;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/22 23:36
 */
@SuperBuilder
@Getter
@ToString(callSuper = true)
public class ArticleStatusUpdatedEvent extends AbstractArticleEvent {

}
