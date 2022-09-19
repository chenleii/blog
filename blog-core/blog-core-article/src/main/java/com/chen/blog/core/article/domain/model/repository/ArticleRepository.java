package com.chen.blog.core.article.domain.model.repository;

import com.chen.blog.core.article.domain.model.Article;
import com.chen.blog.core.article.domain.model.ArticleId;
import com.chen.blog.core.article.domain.model.exception.ArticleNotExistsException;
import com.chen.blog.core.sharedkernel.ddd.annotation.Repository;

import java.util.Optional;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/6 17:34
 */
@Repository
public interface ArticleRepository {



    /**
     * 根据文章ID获取文章
     *
     * @param articleId 文章ID
     * @return 文章
     */
    Article getById(ArticleId articleId);


    /**
     * 根据文章ID获取文章
     * <p>
     * 不存在抛异常
     *
     * @param articleId 文章ID
     * @return 文章
     */
    default Article getByIdNotExistsThrowException(ArticleId articleId) {
        Article article = getById(articleId);
        return Optional.ofNullable(article)
                .orElseThrow(() -> new ArticleNotExistsException("article not exists. " +
                        "articleId:[" + articleId.getId() + "]."));
    }

    /**
     * 保存文章
     *
     * @param article 文章
     */
    void save(Article article);

    void update(Article article);
}
