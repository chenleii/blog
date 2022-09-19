package com.chen.blog.infrastructure.persistence.repository.domainconverter;


import com.chen.blog.core.hot.doamin.model.ArticleHotSearch;
import com.chen.blog.core.sharedkernel.converter.SourceTargetConverter;
import com.chen.blog.core.sharedkernel.serializer.Serializers;
import com.chen.blog.infrastructure.persistence.repository.dataobject.ArticleHotSearchDO;
import org.javamoney.moneta.Money;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/7 16:46
 */
@Mapper(componentModel = "default",
        uses = {IdMapper.class, EnumMapper.class, TimeMapper.class, MonetaryMapper.class, },
        imports = {Serializers.class, Money.class,})
public interface ArticleHotSearchConverter extends SourceTargetConverter<ArticleHotSearch, ArticleHotSearchDO> {
    ArticleHotSearchConverter MAPPER = Mappers.getMapper(ArticleHotSearchConverter.class);


}
