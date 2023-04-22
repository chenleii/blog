package com.chen.blog.infrastructure.persistence.repository.domainconverter;

import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.article.domain.model.ArticleCommentId;
import com.chen.blog.core.article.domain.model.ArticleId;
import com.chen.blog.core.article.domain.model.ArticleSubCommentId;
import com.chen.blog.core.hot.doamin.model.ArticleHotSearchId;
import com.chen.blog.core.notification.domain.model.NotificationId;
import com.chen.blog.core.sharedkernel.converter.Converter;
import com.chen.blog.core.sharedkernel.ddd.Identifiable;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

/**
 * @author cl
 * @version 1.0
 * @since 2021/7/1 18:35
 */
@Mapper(componentModel = MappingConstants.ComponentModel.DEFAULT)
public interface IdMapper extends Converter {
    IdMapper MAPPER = Mappers.getMapper(IdMapper.class);

    default Long map(Identifiable<Long> identifiable) {
        if (Objects.isNull(identifiable)) {
            return null;
        }
        return identifiable.getId();
    }

    default AccountId mapAccountId(Long identifiable) {
        if (Objects.isNull(identifiable)) {
            return null;
        }
        return AccountId.of(identifiable);
    }

    default ArticleId mapArticleId(Long identifiable) {
        if (Objects.isNull(identifiable)) {
            return null;
        }
        return ArticleId.of(identifiable);
    }
    default ArticleCommentId mapCommentId(Long identifiable) {
        if (Objects.isNull(identifiable)) {
            return null;
        }
        return ArticleCommentId.of(identifiable);
    }
    default ArticleSubCommentId mapArticledSubCommentId(Long identifiable) {
        if (Objects.isNull(identifiable)) {
            return null;
        }
        return ArticleSubCommentId.of(identifiable);
    }
    default ArticleHotSearchId mapArticleHotSearchId(Long identifiable) {
        if (Objects.isNull(identifiable)) {
            return null;
        }
        return ArticleHotSearchId.of(identifiable);
    }
    default NotificationId mapNotificationId(Long identifiable) {
        if (Objects.isNull(identifiable)) {
            return null;
        }
        return NotificationId.of(identifiable);
    }
}
