package com.chen.blog.infrastructure.persistence.repository;

import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.core.hot.doamin.model.ArticleHot;
import com.chen.blog.core.hot.doamin.model.cqrs.query.ArticleHotPageQuery;
import com.chen.blog.core.hot.doamin.model.repository.ArticleHotQueryRepository;
import com.chen.blog.core.hot.doamin.model.repository.ArticleHotRepository;
import com.chen.blog.core.article.domain.model.ArticleId;
import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.infrastructure.persistence.repository.dataobject.ArticleHotDO;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.ArticleHotConverter;
import com.chen.blog.infrastructure.persistence.repository.mongodb.ArticleHotMongoRepository;
import com.chen.blog.infrastructure.persistence.repository.mongodb.ArticleMongoRepository;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

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

        Page<ArticleHotDO> page = articleHotMongoRepository.findAll(
                PageRequest.of(Math.toIntExact(pageQuery.getPageIndex()) - 1, Math.toIntExact(pageQuery.getPageSize()))
                        .withSort(Sort.Direction.DESC, "heat","updatedAt")
        );

        Pagination<ArticleRepresentation> defaultPagination = Pagination.create(pageQuery);
        if (Objects.isNull(page)) {
            return defaultPagination;
        }

        List<ArticleHotDO> content = page.getContent();
        if (CollectionUtils.isEmpty(content)) {
            return defaultPagination;
        }

        List<Long> articleIdList = content.stream()
                .map(ArticleHotDO::getArticleId)
                .collect(Collectors.toList());

        return super.batchPageQuery(pageQuery, page.getTotalElements(), pageQuery.getCurrentAccountId(), articleIdList);
    }
}
