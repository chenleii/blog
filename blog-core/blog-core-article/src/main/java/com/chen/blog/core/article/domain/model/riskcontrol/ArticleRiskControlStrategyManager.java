package com.chen.blog.core.article.domain.model.riskcontrol;

import com.chen.blog.core.article.domain.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.annotation.Order;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/27 15:17
 */
@Slf4j
@Named
public class ArticleRiskControlStrategyManager {

    @Inject
    @Order
    private List<ArticleRiskControlStrategy> articleRiskControlStrategyList;

    public void handle(Article article) {
        if (CollectionUtils.isEmpty(articleRiskControlStrategyList)) {
            return;
        }
        for (ArticleRiskControlStrategy articleRiskControlStrategy : articleRiskControlStrategyList) {
            if (!articleRiskControlStrategy.isSupported(article)) {
                continue;
            }
            articleRiskControlStrategy.handle(article);
        }
    }
}
