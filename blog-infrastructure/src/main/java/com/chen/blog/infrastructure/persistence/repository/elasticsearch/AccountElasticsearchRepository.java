package com.chen.blog.infrastructure.persistence.repository.elasticsearch;

import com.chen.blog.infrastructure.persistence.repository.dataobject.AccountDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.inject.Named;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 15:43
 */
@Named
public interface AccountElasticsearchRepository extends ElasticsearchRepository<AccountDO,Long> {
}
