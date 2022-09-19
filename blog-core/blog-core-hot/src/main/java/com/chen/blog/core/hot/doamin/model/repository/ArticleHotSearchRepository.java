package com.chen.blog.core.hot.doamin.model.repository;

import com.chen.blog.core.article.domain.model.exception.ArticleNotExistsException;
import com.chen.blog.core.hot.doamin.model.ArticleHotSearch;
import com.chen.blog.core.sharedkernel.ddd.annotation.Repository;

import java.util.Optional;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/6 17:34
 */
@Repository
public interface ArticleHotSearchRepository {

    ArticleHotSearch getByKeywords(String keywords);

    default ArticleHotSearch getByKeywordsNotExistsThrowException(String keywords) {
        ArticleHotSearch articleHotSearch = getByKeywords(keywords);
        return Optional.ofNullable(articleHotSearch)
                .orElseThrow(() -> new ArticleNotExistsException("articleHotSearch not exists. " +
                        "keywords:[" + keywords + "]."));
    }

    void save(ArticleHotSearch articleHotSearch);

    void update(ArticleHotSearch articleHotSearch);
}
