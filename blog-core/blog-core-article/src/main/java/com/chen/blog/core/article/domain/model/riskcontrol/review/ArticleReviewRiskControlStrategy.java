package com.chen.blog.core.article.domain.model.riskcontrol.review;

import com.chen.blog.core.article.domain.model.Article;
import com.chen.blog.core.article.domain.model.ArticleStatus;
import com.chen.blog.core.article.domain.model.exception.ArticleRiskControlException;
import com.chen.blog.core.article.domain.model.riskcontrol.ArticleRiskControlStrategy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 文章审核风控策略
 *
 * @author cl
 * @version 1.0
 * @since 2022/8/21 8:53
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public interface ArticleReviewRiskControlStrategy extends ArticleRiskControlStrategy {

    /**
     * 转到审核方法处理
     * @param article 文章
     */
    default boolean isSupported(Article article) {
        ArticleStatus status = article.getStatus();
        return status == ArticleStatus.PUBLICATION
                || status == ArticleStatus.VIEWABLE;
    }
    /**
     * 转到审核方法处理
     * @param article 文章
     */
    default void handle(Article article) {
        audit(article);
    }

    /**
     * 文章审核
     *
     * @param article 文章
     * @throws ArticleRiskControlException 审核不通过抛出 {@link ArticleRiskControlException}
     */
    void audit(Article article) throws ArticleRiskControlException;
}
