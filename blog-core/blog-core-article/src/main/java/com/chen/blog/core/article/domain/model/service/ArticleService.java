package com.chen.blog.core.article.domain.model.service;

import com.chen.blog.core.account.domain.model.Account;
import com.chen.blog.core.article.domain.model.Article;
import com.chen.blog.core.article.domain.model.ArticleStatus;
import com.chen.blog.core.article.domain.model.riskcontrol.ArticleRiskControlStrategyManager;
import com.chen.blog.core.sharedkernel.ddd.annotation.DomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 9:08
 */
@DomainService
@Slf4j
@Named
@Validated
public class ArticleService {

    @Inject
    private ArticleRiskControlStrategyManager articleRiskControlStrategyManager;


    public void riskControl(Article article) {
        articleRiskControlStrategyManager.handle(article);
    }


    public void save(Article article, boolean isPublish) {
        if (isPublish) {
            article.publication();

            try {
                riskControl(article);
                article.viewable();
            } catch (Exception e) {
                article.draft();
                throw e;
            }

        }
    }

    public void update(Account account, Article article, String title, Set<String> tags, String content, boolean isPublish) {
        article.update(account, title, tags, content);

        if (article.getStatus() == ArticleStatus.DRAFT) {
            save(article, isPublish);
        }

        // 如果是可见的，更新需要通过审核。
        if (article.getStatus() == ArticleStatus.VIEWABLE) {
            riskControl(article);
        }
    }

    public void report(Article article, Account account, String remark) {
        article.report(account, remark);

        articleRiskControlStrategyManager.handle(article);
    }
}
