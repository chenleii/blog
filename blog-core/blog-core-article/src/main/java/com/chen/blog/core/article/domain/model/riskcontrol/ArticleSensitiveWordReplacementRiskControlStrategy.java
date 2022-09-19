package com.chen.blog.core.article.domain.model.riskcontrol;

import com.chen.blog.core.article.domain.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;
import java.util.Objects;

import static com.chen.blog.core.article.domain.model.riskcontrol.RiskControlConstants.SENSITIVE_WORDS;

/**
 * 文章敏感词替换风控策略
 *
 * @author cl
 * @version 1.0
 * @since 2022/8/27 15:17
 */
@Slf4j
@Named
public class ArticleSensitiveWordReplacementRiskControlStrategy implements ArticleRiskControlStrategy {


    /**
     * 替换的词
     */
    public static final String REPLACEMENT_WORD = "*";

    @Override
    public boolean isSupported(Article article) {
        return true;
    }

    @Override
    public void handle(Article article) {
        if (Objects.isNull(article)) {
            return;
        }

        String title = article.getTitle();
        String content = article.getContent();
        for (String sensitiveWord : SENSITIVE_WORDS) {
            // 转为对应多的替换词
            String replacement = sensitiveWord.chars().mapToObj((item) -> REPLACEMENT_WORD).reduce(String::concat).orElse(null);

            title = StringUtils.replace(title, sensitiveWord, replacement);
            content = StringUtils.replace(content, sensitiveWord, replacement);

        }

        article.update(title, article.getTags(), content);
    }

}
