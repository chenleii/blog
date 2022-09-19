package com.chen.blog.interfaces.http.dto.converter;

import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.core.sharedkernel.converter.SourceToTargetConverter;
import com.chen.blog.interfaces.http.dto.output.ArticleOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default")
public interface ArticleDTOConverter extends SourceToTargetConverter<ArticleRepresentation, ArticleOutputDTO> {
    ArticleDTOConverter MAPPER = Mappers.getMapper(ArticleDTOConverter.class);


}