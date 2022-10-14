package com.chen.blog.infrastructure.persistence.repository;

import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.core.hot.doamin.model.ArticleHotSearch;
import com.chen.blog.core.hot.doamin.model.cqrs.query.ArticleHotSearchPageQuery;
import com.chen.blog.core.hot.doamin.model.repository.ArticleHotSearchQueryRepository;
import com.chen.blog.core.hot.doamin.model.repository.ArticleHotSearchRepository;
import com.chen.blog.infrastructure.persistence.repository.dataobject.ArticleHotSearchDO;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.ArticleHotSearchConverter;
import com.chen.blog.infrastructure.persistence.repository.mongodb.ArticleHotSearchMongoRepository;
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
public class ArticleHotSearchRepositoryImpl implements ArticleHotSearchRepository, ArticleHotSearchQueryRepository {

    @Inject
    private MongoTemplate mongoTemplate;

    @Inject
    private ArticleHotSearchMongoRepository articleHotSearchMongoRepository;


    @Override
    public ArticleHotSearch getByKeywords(String keywords) {
        Preconditions.checkNotNull(keywords);

        Optional<ArticleHotSearchDO> articleHotSearchDO = articleHotSearchMongoRepository.findByKeywords(keywords);
        return ArticleHotSearchConverter.MAPPER.from(articleHotSearchDO.orElse(null));
    }

    @Override
    public void save(ArticleHotSearch articleHotSearch) {
        Preconditions.checkNotNull(articleHotSearch);

        ArticleHotSearchDO articleHotSearchDO = ArticleHotSearchConverter.MAPPER.to(articleHotSearch);
        articleHotSearchMongoRepository.save(articleHotSearchDO);
    }

    @Override
    public void update(ArticleHotSearch articleHotSearch) {
        save(articleHotSearch);
    }

    @Override
    public Pagination<String> pageQuery(ArticleHotSearchPageQuery pageQuery) {
        Preconditions.checkNotNull(pageQuery);

        Page<ArticleHotSearchDO> page = articleHotSearchMongoRepository.findAll(
                PageRequest.of(Math.toIntExact(pageQuery.getPageIndex()) - 1, Math.toIntExact(pageQuery.getPageSize()) + 1)
                        .withSort(Sort.Direction.DESC, "updatedAt", "heat")
        );

        Pagination<String> defaultPagination = Pagination.create(pageQuery);
        if (Objects.isNull(page)) {
            return defaultPagination;
        }

        List<ArticleHotSearchDO> content = page.getContent();
        if (CollectionUtils.isEmpty(content)) {
            return defaultPagination;
        }

        return Pagination.create(pageQuery,
                page.getTotalElements(),
                content.stream()
                        .map(ArticleHotSearchDO::getKeywords)
                        .collect(Collectors.toList())
        );

    }
}
