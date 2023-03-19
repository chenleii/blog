package com.chen.blog.infrastructure.persistence.repository;

import com.chen.blog.core.article.domain.model.ArticleId;
import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.core.hot.doamin.model.ArticleHot;
import com.chen.blog.core.hot.doamin.model.cqrs.query.ArticleHotPageQuery;
import com.chen.blog.core.hot.doamin.model.repository.ArticleHotQueryRepository;
import com.chen.blog.core.hot.doamin.model.repository.ArticleHotRepository;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.infrastructure.persistence.repository.dataobject.ArticleHotDO;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.ArticleHotConverter;
import com.chen.blog.infrastructure.persistence.repository.mongodb.ArticleHotMongoRepository;
import com.chen.blog.infrastructure.persistence.repository.mongodb.ArticleMongoRepository;
import com.google.common.base.Preconditions;
import com.mongodb.client.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoExpression;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/7 16:41
 */
@Named
@Slf4j
public class ArticleHotRepositoryImpl extends ArticleRepositoryImpl implements ArticleHotRepository, ArticleHotQueryRepository {

    @Inject
    private MongoTemplate mongoTemplate;
    private MongoClient mongoClient;

    @Inject
    private ArticleHotMongoRepository articleHotMongoRepository;

    @Inject
    private ArticleMongoRepository articleMongoRepository;


    @Override
    public ArticleHot getByArticleId(ArticleId articleId) {
        Preconditions.checkNotNull(articleId);

        Optional<ArticleHotDO> articleHotDO = articleHotMongoRepository.findById(articleId.getId());
        return ArticleHotConverter.MAPPER.from(articleHotDO.orElse(null));
    }

    @Override
    public void save(ArticleHot articleHot) {
        Preconditions.checkNotNull(articleHot);

        ArticleHotDO articleHotDO = ArticleHotConverter.MAPPER.to(articleHot);
        articleHotMongoRepository.save(articleHotDO);
    }

    @Override
    public void update(ArticleHot articleHot) {
        save(articleHot);
    }

    @Override
    public void deleteById(ArticleId articleId) {
        Preconditions.checkNotNull(articleId);

        articleHotMongoRepository.deleteById(articleId.getId());
    }


    @Override
    public Pagination<ArticleRepresentation> pageQuery(ArticleHotPageQuery pageQuery) {
        Preconditions.checkNotNull(pageQuery);

        // 查询总条数
        final long count = articleHotMongoRepository.count();
        // 查询当前页数据，按照日期+热度值+id排序。
        final AggregationResults<ArticleHotDO> articleHotResult = mongoTemplate.aggregateAndReturn(ArticleHotDO.class)
                .by(
                        Aggregation.newAggregation(
                                Aggregation.project(ArticleHotDO.class)
                                        .andInclude("id", "heat")
                                        .and(AggregationExpression.from(
                                                MongoExpression.create(
                                                        "$dateToString:{" +
                                                                "format:'%Y%m%d'," +
                                                                "date:{$add:[new Date(0),'$updatedAt']}," +
                                                                "timezone: 'Asia/Shanghai'" +
                                                                "}"))
                                        ).as("date"),
                                Aggregation.sort(Sort.by(Sort.Direction.DESC, "date", "heat", "id")),
                                Aggregation.skip((pageQuery.getPageIndex() - 1) * pageQuery.getPageSize()),
                                Aggregation.limit(pageQuery.getPageSize())
                        )
                ).all();

        Pagination<ArticleRepresentation> defaultPagination = Pagination.create(pageQuery);
        if (Objects.isNull(articleHotResult)) {
            return defaultPagination;
        }

        final List<ArticleHotDO> mappedResults = articleHotResult.getMappedResults();
        if (CollectionUtils.isEmpty(mappedResults)) {
            return defaultPagination;
        }

        List<Long> articleIdList = mappedResults.stream()
                .map(ArticleHotDO::getArticleId)
                .collect(Collectors.toList());

        return super.batchPageQuery(pageQuery, count, pageQuery.getCurrentAccountId(), articleIdList);
    }
}
