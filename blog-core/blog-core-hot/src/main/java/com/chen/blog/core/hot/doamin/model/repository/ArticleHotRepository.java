package com.chen.blog.core.hot.doamin.model.repository;

import com.chen.blog.core.article.domain.model.ArticleId;
import com.chen.blog.core.article.domain.model.exception.ArticleNotExistsException;
import com.chen.blog.core.hot.doamin.model.ArticleHot;
import com.chen.blog.core.sharedkernel.ddd.annotation.Repository;

import java.util.Optional;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/6 17:34
 */
@Repository
public interface ArticleHotRepository {



    ArticleHot getByArticleId(ArticleId articleId);

    default ArticleHot getByArticleIdNotExistsThrowException(ArticleId articleId) {
        ArticleHot articleHot = getByArticleId(articleId);
        return Optional.ofNullable(articleHot)
                .orElseThrow(() -> new ArticleNotExistsException("articleHot not exists. " +
                        "articleId:[" + articleId.getId() + "]."));
    }

    void save(ArticleHot articleHot);

    void update(ArticleHot articleHot);

    void deleteById(ArticleId articleId);
}
