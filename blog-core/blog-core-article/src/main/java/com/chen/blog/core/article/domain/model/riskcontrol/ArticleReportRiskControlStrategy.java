package com.chen.blog.core.article.domain.model.riskcontrol;

import com.chen.blog.core.article.domain.model.Article;
import com.chen.blog.core.article.domain.model.ArticleStatus;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Named;
import java.util.Objects;

/**
 * 文章举报风控策略
 *
 * @author cl
 * @version 1.0
 * @since 2022/8/27 15:17
 */
@Slf4j
@Named
public class ArticleReportRiskControlStrategy implements ArticleRiskControlStrategy {

    /**
     * 举报风控阈值
     */
    public static final int REPORT_NUMBER = 10;


    @Override
    public boolean isSupported(Article article) {
        ArticleStatus status = article.getStatus();
        return status == ArticleStatus.VIEWABLE;
    }

    @Override
    public void handle(Article article) {
        if (Objects.isNull(article)) {
            return;
        }

        if (article.reportNumber() > REPORT_NUMBER) {
            // 不再展示
            article.invisible();
        }
    }
}
