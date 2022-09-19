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
public class ArticleReportedEvent extends AbstractArticleEvent {


    /**
     * 举报的账户ID
     */
    private Long reportedAccountId;

    /**
     * 举报备注
     */
    private String remark;
}
