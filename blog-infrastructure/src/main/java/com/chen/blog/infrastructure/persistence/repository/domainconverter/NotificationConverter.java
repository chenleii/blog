package com.chen.blog.infrastructure.persistence.repository.domainconverter;

import com.chen.blog.core.notification.domain.model.Notification;
import com.chen.blog.core.notification.domain.model.NotificationDetailsAdditionalInfo;
import com.chen.blog.core.sharedkernel.converter.SourceTargetConverter;
import com.chen.blog.core.sharedkernel.serializer.Serializers;
import com.chen.blog.infrastructure.persistence.repository.dataobject.NotificationDO;
import org.javamoney.moneta.Money;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

/**
 * @author cl
 * @version 1.0
 * @since 2023/4/12 23:01
 */
@Mapper(componentModel = MappingConstants.ComponentModel.DEFAULT,
        uses = {IdMapper.class, EnumMapper.class, TimeMapper.class, MonetaryMapper.class,},
        imports = {Serializers.class, Money.class,})
public interface NotificationConverter extends SourceTargetConverter<Notification, NotificationDO> {
    NotificationConverter MAPPER = Mappers.getMapper(NotificationConverter.class);

    @SubclassMapping(target = NotificationDetailsAdditionalInfo.AccountDisabled.class, source = NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo.AccountDisabled.class)
    @SubclassMapping(target = NotificationDetailsAdditionalInfo.ArticleLiked.class, source = NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo.ArticleLiked.class)
    @SubclassMapping(target = NotificationDetailsAdditionalInfo.ArticleComment.class, source = NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo.ArticleComment.class)
    NotificationDetailsAdditionalInfo map(NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo notificationDetailsAdditionalInfo);

    @SubclassMapping(source = NotificationDetailsAdditionalInfo.AccountDisabled.class, target = NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo.AccountDisabled.class)
    @SubclassMapping(source = NotificationDetailsAdditionalInfo.ArticleLiked.class, target = NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo.ArticleLiked.class)
    @SubclassMapping(source = NotificationDetailsAdditionalInfo.ArticleComment.class, target = NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo.ArticleComment.class)
    NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo map(NotificationDetailsAdditionalInfo notificationDetailsAdditionalInfo);


    default NotificationDetailsAdditionalInfo factory1(){
        return null;
    }
    default NotificationDO.NotificationDetails.NotificationDetailsAdditionalInfo factory2(){
        return null;
    }

}
