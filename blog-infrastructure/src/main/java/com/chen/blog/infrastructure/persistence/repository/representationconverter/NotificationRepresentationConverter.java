package com.chen.blog.infrastructure.persistence.repository.representationconverter;

import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.core.notification.domain.model.NotificationDetailsAdditionalInfo;
import com.chen.blog.core.notification.domain.model.cqrs.representation.NotificationRepresentation;
import com.chen.blog.core.sharedkernel.converter.SourceFromTargetConverter;
import com.chen.blog.core.sharedkernel.serializer.Serializers;
import com.chen.blog.infrastructure.persistence.repository.dataobject.ArticleDO;
import com.chen.blog.infrastructure.persistence.repository.dataobject.NotificationDO;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.EnumMapper;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.IdMapper;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.MonetaryMapper;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.TimeMapper;
import org.javamoney.moneta.Money;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/7 16:56
 */
@Mapper(componentModel = MappingConstants.ComponentModel.DEFAULT,
        uses = {IdMapper.class, EnumMapper.class, TimeMapper.class, MonetaryMapper.class,},
        imports = {Serializers.class, Money.class,})
public interface NotificationRepresentationConverter extends SourceFromTargetConverter<NotificationRepresentation, NotificationDO> {
    NotificationRepresentationConverter MAPPER = Mappers.getMapper(NotificationRepresentationConverter.class);

    @SubclassMapping(target = NotificationRepresentation.NotificationDetails.NotificationDetailsAdditionalInfo.AccountDisabled.class, source = NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo.AccountDisabled.class)
    @SubclassMapping(target = NotificationRepresentation.NotificationDetails.NotificationDetailsAdditionalInfo.ArticleLiked.class, source = NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo.ArticleLiked.class)
    @SubclassMapping(target = NotificationRepresentation.NotificationDetails.NotificationDetailsAdditionalInfo.ArticleComment.class, source = NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo.ArticleComment.class)
    NotificationRepresentation.NotificationDetails.NotificationDetailsAdditionalInfo map(NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo notificationDetailsAdditionalInfo);

    @SubclassMapping(source = NotificationRepresentation.NotificationDetails.NotificationDetailsAdditionalInfo.AccountDisabled.class, target = NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo.AccountDisabled.class)
    @SubclassMapping(source = NotificationRepresentation.NotificationDetails.NotificationDetailsAdditionalInfo.ArticleLiked.class, target = NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo.ArticleLiked.class)
    @SubclassMapping(source = NotificationRepresentation.NotificationDetails.NotificationDetailsAdditionalInfo.ArticleComment.class, target = NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo.ArticleComment.class)
    NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo map(NotificationRepresentation.NotificationDetails.NotificationDetailsAdditionalInfo notificationDetailsAdditionalInfo);

    default NotificationRepresentation.NotificationDetails.NotificationDetailsAdditionalInfo factory1(){
        return null;
    }
    default NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo factory2(){
        return null;
    }
}
