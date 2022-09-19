package com.chen.blog.core.article.domain.model.riskcontrol;

import com.chen.blog.core.article.domain.model.Article;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/27 15:17
 */
public interface ArticleRiskControlStrategy {

    /**
     * 是否支持的
     *
     * @param article 文章
     * @return 是/否
     */
    boolean isSupported(Article article);

    /**
     * 风控处理
     *
     * @param article 文章
     */
    void handle(Article article);
}
