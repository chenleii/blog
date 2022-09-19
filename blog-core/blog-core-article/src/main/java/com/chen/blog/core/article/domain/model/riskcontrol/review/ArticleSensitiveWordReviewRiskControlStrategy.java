package com.chen.blog.core.article.domain.model.riskcontrol.review;

import com.chen.blog.core.article.domain.model.Article;
import com.chen.blog.core.article.domain.model.exception.ArticleRiskControlException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;

import static com.chen.blog.core.article.domain.model.riskcontrol.RiskControlConstants.SENSITIVE_WORDS;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 8:55
 */
@Slf4j
@Named
public class ArticleSensitiveWordReviewRiskControlStrategy implements ArticleReviewRiskControlStrategy {

    @Override
    public void audit(Article article) {
        for (String sensitiveWord : SENSITIVE_WORDS) {
            if (StringUtils.contains(article.getTitle(), sensitiveWord)) {
                throw new ArticleRiskControlException("article title review not pass.");
            }
            if (StringUtils.contains(article.getContent(), sensitiveWord)) {
                throw new ArticleRiskControlException("article content review not pass.");
            }
            for (String tag : article.getTags()) {
                if (StringUtils.contains(tag, sensitiveWord)) {
                    throw new ArticleRiskControlException("article tags review not pass.");
                }
            }
        }
    }
}
