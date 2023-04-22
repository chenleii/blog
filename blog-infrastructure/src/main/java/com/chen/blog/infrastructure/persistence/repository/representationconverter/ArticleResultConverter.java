package com.chen.blog.infrastructure.persistence.repository.representationconverter;

import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.core.sharedkernel.converter.SourceFromTargetConverter;
import com.chen.blog.core.sharedkernel.serializer.Serializers;
import com.chen.blog.infrastructure.persistence.repository.dataobject.ArticleDO;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.EnumMapper;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.IdMapper;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.MonetaryMapper;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.TimeMapper;
import jakarta.inject.Inject;
import org.javamoney.moneta.Money;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/7 16:56
 */
@Mapper(componentModel = MappingConstants.ComponentModel.DEFAULT,
        uses = {IdMapper.class, EnumMapper.class, TimeMapper.class, MonetaryMapper.class,},
        imports = {Serializers.class, Money.class,})
public interface ArticleResultConverter extends SourceFromTargetConverter<ArticleRepresentation, ArticleDO> {
    ArticleResultConverter MAPPER = Mappers.getMapper(ArticleResultConverter.class);


}
