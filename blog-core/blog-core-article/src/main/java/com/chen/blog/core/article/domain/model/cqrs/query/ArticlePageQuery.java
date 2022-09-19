package com.chen.blog.core.article.domain.model.cqrs.query;

import com.chen.blog.core.sharedkernel.cqrs.PageQuery;
import com.chen.blog.core.article.domain.model.ArticleStatus;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:10
 */
@Getter
@ToString
@SuperBuilder
public class ArticlePageQuery extends PageQuery {

    /**
     * 当前的账户ID
     */
    private final Long currentAccountId;



    /**
     * 搜索关键字
     */
    private final String searchKeyword;

    /**
     * 查询的账户ID
     */
    private final Long accountId;
    /**
     * 查询的状态
     */
    private final Set<ArticleStatus> statuses;

    /**
     * 是否开启拼音搜索
     */
    private final Boolean isOpenPinyin;
    /**
     * 是否开启同义词搜索
     */
    private final Boolean isOpenSynonym;
    /**
     * 是否开启跨语言搜索
     */
    private final Boolean isOpenCrossLanguage;
}
