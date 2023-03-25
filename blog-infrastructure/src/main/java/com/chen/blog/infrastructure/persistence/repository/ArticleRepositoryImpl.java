package com.chen.blog.infrastructure.persistence.repository;

import co.elastic.clients.elasticsearch._types.*;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.chen.blog.core.account.domain.model.cqrs.representation.AccountRepresentation;
import com.chen.blog.core.article.domain.model.Article;
import com.chen.blog.core.article.domain.model.ArticleId;
import com.chen.blog.core.article.domain.model.ArticleStatus;
import com.chen.blog.core.article.domain.model.cqrs.query.ArticlePageQuery;
import com.chen.blog.core.article.domain.model.cqrs.query.ArticleQuery;
import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.core.article.domain.model.repository.ArticleQueryRepository;
import com.chen.blog.core.article.domain.model.repository.ArticleRepository;
import com.chen.blog.core.sharedkernel.cqrs.PageQuery;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.infrastructure.persistence.repository.dataobject.AccountDO;
import com.chen.blog.infrastructure.persistence.repository.dataobject.ArticleDO;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.ArticleConverter;
import com.chen.blog.infrastructure.persistence.repository.elasticsearch.ArticleElasticsearchRepository;
import com.chen.blog.infrastructure.persistence.repository.mongodb.AccountMongoRepository;
import com.chen.blog.infrastructure.persistence.repository.mongodb.ArticleMongoRepository;
import com.chen.blog.infrastructure.persistence.repository.representationconverter.AccountResultConverter;
import com.chen.blog.infrastructure.persistence.repository.representationconverter.ArticleResultConverter;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
import org.springframework.data.elasticsearch.client.erhlc.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.client.erhlc.HighlightQueryBuilder;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQuery;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.data.mongodb.core.MongoTemplate;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/7 16:41
 */
@Primary
@Named
@Slf4j
public class ArticleRepositoryImpl implements ArticleRepository, ArticleQueryRepository {

    public static final String HIGHLIGHT_PRE = "<font color=\"red\">";
    public static final String HIGHLIGHT_POST = "</font>";
    @Inject
    private ElasticsearchTemplate elasticsearchTemplate;
    @Inject
    private ArticleElasticsearchRepository articleElasticsearchRepository;

    @Inject
    private ArticleMongoRepository articleMongoRepository;
    @Inject
    private AccountMongoRepository accountMongoRepository;

    @Inject
    private MongoTemplate mongoTemplate;

    @Override
    public Article getById(ArticleId articleId) {
        Preconditions.checkNotNull(articleId);

        Optional<ArticleDO> articleDO = articleMongoRepository.findById(articleId.getId());
        return ArticleConverter.MAPPER.from(articleDO.orElse(null));
    }

    @Override
    public void save(Article article) {
        Preconditions.checkNotNull(article);

        ArticleDO articleDO = ArticleConverter.MAPPER.to(article);
        articleMongoRepository.save(articleDO);
        articleElasticsearchRepository.save(articleDO);
    }

    @Override
    public void update(Article article) {
        save(article);
    }


    @Override
    public Pagination<ArticleRepresentation> pageQuery(ArticlePageQuery pageQuery) {
        Preconditions.checkNotNull(pageQuery);

        // 获取查询参数
        String searchKeyword = pageQuery.getSearchKeyword();
        Long accountId = pageQuery.getAccountId();
        Set<ArticleStatus> statuses = pageQuery.getStatuses();
        int pageSize = Math.toIntExact(pageQuery.getPageSize());
        int pageIndex = Math.toIntExact(pageQuery.getPageIndex()) - 1;
        List<Object> lastValues = pageQuery.getLastValues();

        NativeQueryBuilder nativeQueryBuilder = NativeQuery.builder()
                // 查询条件
                .withQuery((queryBuilder) -> {
                    queryBuilder.bool((boolQueryBuilder) -> {
                        if (StringUtils.isNotBlank(searchKeyword)) {
                            boolQueryBuilder.must((mustBuilder) ->
                                    mustBuilder.bool((keywordBoolQuerBuilder) ->
                                            keywordBoolQuerBuilder.should((keywordBoolShouldQuerBuilder) ->
                                                    keywordBoolShouldQuerBuilder.match((matchBuilder ->
                                                            matchBuilder.field("title").query(searchKeyword)))
                                            ).should((keywordBoolShouldQuerBuilder) ->
                                                    keywordBoolShouldQuerBuilder.match((matchBuilder ->
                                                            matchBuilder.field("tags").query(searchKeyword)))
                                            ).should((keywordBoolShouldQuerBuilder) ->
                                                    keywordBoolShouldQuerBuilder.match((matchBuilder ->
                                                            matchBuilder.field("content").query(searchKeyword)))
                                            )
                                    )
                            );
                        }
                        if (Objects.nonNull(accountId)) {
                            boolQueryBuilder.must((mustBuilder) ->
                                    mustBuilder.match((matchBuilder) ->
                                            matchBuilder.field("accountId").query(accountId)
                                    )
                            );
                        }
                        if (CollectionUtils.isNotEmpty(statuses)) {
                            boolQueryBuilder.must((mustBuilder) ->
                                    mustBuilder.terms((matchBuilder) ->
                                            matchBuilder.field("status").terms((termsQueryField) ->
                                                    termsQueryField.value(statuses.stream()
                                                            .map(ArticleStatus::name)
                                                            .map(FieldValue::of)
                                                            .collect(Collectors.toList()))
                                            )
                                    )
                            );
                        }

                        return boolQueryBuilder;
                    });
                    return queryBuilder;
                })
                // 设置查询高亮
                .withHighlightQuery(
                        new HighlightQuery(
                                new Highlight(
                                        HighlightParameters.builder()
                                                .withPreTags(HIGHLIGHT_PRE)
                                                .withPostTags(HIGHLIGHT_POST)
                                                .build(),
                                        List.of(
                                                new HighlightField("title"),
                                                new HighlightField("tags"),
                                                new HighlightField("content")
                                        )
                                ),
                                ArticleDO.class
                        )
                );

        // 分页查询，优先使用es的search after优化
        if (CollectionUtils.isNotEmpty(lastValues) && pageIndex > 0) {
            nativeQueryBuilder
                    .withPageable(PageRequest.ofSize(pageSize))
                    .withSearchAfter(lastValues);
        } else {
            nativeQueryBuilder
                    .withPageable(PageRequest.of(pageIndex, pageSize));
        }

        // 排序，关键词搜索优先按相关性分数倒序
        if (StringUtils.isNotBlank(searchKeyword)) {
            nativeQueryBuilder.withSort((sortOptionsBuilder) ->
                    sortOptionsBuilder.score((scoreBuilder) ->
                            scoreBuilder.order(SortOrder.Desc)
                    )
            );
        }
        nativeQueryBuilder.withSort((sortOptionsBuilder) ->
                sortOptionsBuilder.field((filedBuilder) ->
                        filedBuilder.field("updatedAt").order(SortOrder.Desc)
                )
        );
        nativeQueryBuilder.withSort((sortOptionsBuilder) ->
                sortOptionsBuilder.field((filedBuilder) ->
                        filedBuilder.field("id").order(SortOrder.Desc)
                )
        );

        NativeQuery nativeQuery = nativeQueryBuilder.build();
        SearchHits<ArticleDO> searchHits = elasticsearchTemplate.search(nativeQuery, ArticleDO.class);

        List<SearchHit<ArticleDO>> searchHitList = searchHits.getSearchHits();
        List<ArticleDO> articleDOList = searchHitList.stream()
                .peek((item) -> {
                    // 将高亮字段设置回实体
                    List<String> titleList = item.getHighlightField("title");
                    if (CollectionUtils.isNotEmpty(titleList)) {
                        item.getContent().setTitle(titleList.get(0));
                    }
                    List<String> contentList = item.getHighlightField("content");
                    if (CollectionUtils.isNotEmpty(contentList)) {
                        item.getContent().setContent(contentList.get(0));
                    }

                })
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        List<ArticleRepresentation> list = ArticleResultConverter.MAPPER.listFromList(articleDOList);
        // 补全其他信息信息
        setOther(pageQuery.getCurrentAccountId(), list);
        List<Object> resultLastValues = CollectionUtils.isNotEmpty(searchHitList) ? searchHitList.get(searchHitList.size() - 1).getSortValues() : null;
        return Pagination.create(pageQuery,
                searchHits.getTotalHits(), list, resultLastValues);
    }

    protected Pagination<ArticleRepresentation> batchPageQuery(PageQuery pageQuery, Long total, Long accountId, List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Pagination.create(pageQuery);
        }
        List<ArticleDO> articleDOList = IterableUtils.toList(articleMongoRepository.findAllById(ids));
        List<ArticleRepresentation> list = ArticleResultConverter.MAPPER.listFromList(articleDOList);
        // 保证按照批量查询ids的顺序返回
        list = list.stream()
                .sorted(Comparator.comparingInt(v -> ids.indexOf(v.getId())))
                .collect(Collectors.toList());
        // 补全其他信息信息
        setOther(accountId, list);
        return Pagination.create(pageQuery,
                total,
                list);
    }

    @Override
    public ArticleRepresentation queryById(ArticleQuery query) {
        Preconditions.checkNotNull(query);
        Long currentAccountId = query.getCurrentAccountId();
        Long articleId = query.getArticleId();

        if (Objects.isNull(articleId)) {
            return null;
        }

        Optional<ArticleDO> articleDO = articleMongoRepository.findById(articleId);
        return articleDO
                .map(ArticleResultConverter.MAPPER::from)
                .map((articleResult) -> {
                    // 补全其他信息信息
                    setOther(currentAccountId, Collections.singletonList(articleResult));
                    return articleResult;
                }).orElse(null);

    }

    private void setOther(Long accountId, List<ArticleRepresentation> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        setAccount(list);

        for (ArticleRepresentation articleResult : list) {

            articleResult.setLikedNumber(likeNumber(articleResult, accountId));
            articleResult.setReportedNumber(reportNumber(articleResult, accountId));
            articleResult.setIsLiked(isLiked(articleResult, accountId));
            articleResult.setIsReported(isReported(articleResult, accountId));
            articleResult.setCustomContent(getCustomContent(articleResult));
            articleResult.setCover(getCover(articleResult));
        }
    }

    private int likeNumber(ArticleRepresentation articleResult, Long likeAccountId) {
        return articleResult.getLikes().stream()
                .filter((item) -> Objects.isNull(likeAccountId) || Objects.equals(item.getAccountId(), likeAccountId))
                .mapToInt((item) -> item.getIsLiked() ? 1 : -1)
                .sum();

    }

    public boolean isLiked(ArticleRepresentation articleResult, Long likeAccountId) {
        if (Objects.isNull(likeAccountId)) {
            return false;
        }
        return likeNumber(articleResult, likeAccountId) > 0;
    }

    private int reportNumber(ArticleRepresentation articleResult, Long reportAccountId) {
        return articleResult.getReports().stream()
                .filter((item) -> Objects.isNull(reportAccountId) || Objects.equals(item.getAccountId(), reportAccountId))
                .mapToInt((item) -> 1)
                .sum();
    }

    public boolean isReported(ArticleRepresentation articleResult, Long reportAccountId) {
        if (Objects.isNull(reportAccountId)) {
            return false;
        }

        return reportNumber(articleResult, reportAccountId) > 0;
    }

    public String getCustomContent(ArticleRepresentation articleResult) {
        int i = StringUtils.indexOf(articleResult.getContent(), HIGHLIGHT_PRE);
        if (i != -1) {
            return StringUtils.substring(articleResult.getContent(), i);
        }
        return articleResult.getContent();
    }

    public String getCover(ArticleRepresentation articleResult) {
        {
            // 匹配示例: ![image.png](https://segmentfault.com/img/remote/1460000042529346 "dump 虚拟线程")
            Pattern compile = Pattern.compile("!\\[.*\\]\\(((?=.+\\s.*).+?(?=\\s)|.+).*\\)");
            Matcher matcher = compile.matcher(articleResult.getContent());
            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        {
            Pattern compile = Pattern.compile("((?<protocol>https?)://(?<host>[\\-\\w]+(?:\\.[\\-\\w]*)*)(?::(?<port>\\d{1,5}))?(?<path>(?:/[\\-\\w.]*)*(?<imgpath>/[\\-\\w.]*(?:\\.png|\\.jpeg|\\.jpg|\\.gif)[\\-!\\w.]*))(?<param>\\?(?:[\\-\\w]*=?[\\-\\w]*&?)*)?)");
            Matcher matcher = compile.matcher(articleResult.getContent());
            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        return null;
    }


    private void setAccount(List<ArticleRepresentation> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        Set<Long> accountIds = new HashSet<>();

        List<Long> articleAccountIds = list.stream()
                .map(ArticleRepresentation::getAccountId)
                .collect(Collectors.toList());
        accountIds.addAll(articleAccountIds);
        List<Long> articleCommentAccountIds = list.stream()
                .flatMap((item) -> item.getComments().stream())
                .flatMap((item) -> Stream.concat(Stream.of(item), item.getSubComments().stream()))
                .map(ArticleRepresentation.ArticleCommentDO::getAccountId)
                .collect(Collectors.toList());
        accountIds.addAll(articleCommentAccountIds);

        if (CollectionUtils.isEmpty(accountIds)) {
            return;
        }
        List<AccountDO> accounts = IterableUtils.toList(accountMongoRepository.findAllById(accountIds));
        if (CollectionUtils.isEmpty(accounts)) {
            return;
        }

        Map<Long, AccountRepresentation> accountMap = accounts.stream()
                .map(AccountResultConverter.MAPPER::from)
                .peek((item) -> item.setPassword(null))
                .collect(Collectors.toMap(AccountRepresentation::getId, (item) -> item));
        // 设置账户
        for (ArticleRepresentation articleResult : list) {
            articleResult.setAccount(accountMap.get(articleResult.getAccountId()));

            articleResult.getComments().stream()
                    .flatMap((item) -> Stream.concat(Stream.of(item), item.getSubComments().stream()))
                    .forEach((articleCommentDO) -> {
                        articleCommentDO.setAccount(accountMap.get(articleCommentDO.getAccountId()));
                    });
        }


    }


}
