package com.chen.blog.infrastructure.persistence.repository.domainconverter;

import com.chen.blog.core.article.domain.model.Article;
import com.chen.blog.core.sharedkernel.converter.SourceTargetConverter;
import com.chen.blog.core.sharedkernel.serializer.Serializers;
import com.chen.blog.infrastructure.persistence.repository.dataobject.ArticleDO;
import org.javamoney.moneta.Money;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/7 16:46
 */
@Mapper(componentModel = MappingConstants.ComponentModel.DEFAULT ,
        uses = {IdMapper.class, EnumMapper.class, TimeMapper.class, MonetaryMapper.class,},
        imports = {Serializers.class, Money.class,})
public interface ArticleConverter extends SourceTargetConverter<Article, ArticleDO> {
    ArticleConverter MAPPER = Mappers.getMapper(ArticleConverter.class);


}
