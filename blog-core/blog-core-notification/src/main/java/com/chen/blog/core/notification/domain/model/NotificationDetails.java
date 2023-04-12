package com.chen.blog.core.notification.domain.model;

import com.chen.blog.core.sharedkernel.ddd.annotation.ValueObject;
import com.google.common.base.Preconditions;
import lombok.*;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/8 22:21
 */
@ValueObject
@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public class NotificationDetails {

    /**
     * 类型
     */
    private NotificationDetailsType type;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 额外信息
     */
    private NotificationDetailsAdditionalInfo additionalInfo;

    private static NotificationDetails create(NotificationDetailsType type, NotificationDetailsAdditionalInfo additionalInfo) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(additionalInfo);

        final String title = type.generateTitle(additionalInfo);
        final String content = type.generateContent(additionalInfo);

        return NotificationDetails.builder()
                .type(type)
                .title(title)
                .content(content)
                .additionalInfo(additionalInfo)
                .build();
    }

    public static NotificationDetails accountDisabled(
            NotificationDetailsAdditionalInfo.AccountDisabled additionalInfo) {
        return create(NotificationDetailsType.ACCOUNT_DISABLED, additionalInfo);
    }

    public static NotificationDetails articleLiked(
            NotificationDetailsAdditionalInfo.ArticleLiked additionalInfo) {
        return create(NotificationDetailsType.ARTICLE_LIKE, additionalInfo);
    }

    public static NotificationDetails articleComment(
            NotificationDetailsAdditionalInfo.ArticleComment additionalInfo) {
        return create(NotificationDetailsType.ARTICLE_COMMENT, additionalInfo);
    }

}
