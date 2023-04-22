package com.chen.blog.infrastructure.persistence.repository.dataobject;

import com.chen.blog.core.notification.domain.model.NotificationDetailsAdditionalInfo;
import com.chen.blog.core.notification.domain.model.NotificationDetailsType;
import com.chen.blog.core.notification.domain.model.NotificationStatus;
import com.chen.blog.core.sharedkernel.ddd.annotation.ValueObject;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.util.List;

/**
 * @author cl
 * @since 2023/4/12 21:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@org.springframework.data.mongodb.core.mapping.Document(collection = "notification")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "notification")
public class NotificationDO implements Serializable {

    /**
     * ID
     */
    @Id
    private Long id;


    /**
     * 账户ID
     */
    @Indexed
    private Long accountId;
    /**
     * 详情
     */
    private NotificationDetails details;

    /**
     * 状态
     */
    @Indexed
    private NotificationStatus status;


    /**
     * 创建于
     */
    @Indexed
    private Long createdAt;
    /**
     * 已读于
     */
    @Indexed
    private Long hasReadAt;
    /**
     * 清除于
     */
    @Indexed
    private Long clearedAt;

    @Data
    public static class NotificationDetails implements Serializable {

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


        public static abstract class NotificationDetailsAdditionalInfo implements Serializable {


            @Data
            public static class AccountDisabled extends NotificationDetailsAdditionalInfo implements Serializable {

                /**
                 * 禁用的账户ID
                 */
                private Long accountId;
                /**
                 * 禁用的账户名称
                 */
                private String accountName;

            }


            @Data
            public static class ArticleLiked extends NotificationDetailsAdditionalInfo implements Serializable {

                /**
                 * 文章ID
                 */
                private Long articleId;
                /**
                 * 文章标题
                 */
                private String articleTitle;

                /**
                 * 点赞的账户ID
                 */
                private Long accountId;
                /**
                 * 点赞的账户名称
                 */
                private String accountName;

            }


            @Data
            public static class ArticleComment extends NotificationDetailsAdditionalInfo implements Serializable {

                /**
                 * 文章ID
                 */
                private Long articleId;
                /**
                 * 文章标题
                 */
                private String articleTitle;

                /**
                 * 文章评论ID
                 */
                private Long articleCommentId;

                /**
                 * 文章子评论ID
                 */
                private Long articleSubCommentId;
                /**
                 * 文章回复的子评论ID
                 */
                private Long articleReplySubCommentId;

                /**
                 * 评论的账户ID
                 */
                private Long accountId;
                /**
                 * 评论的账户名称
                 */
                private String accountName;
                /**
                 * 评论内容艾特的账户ID列表
                 */
                private List<Long> atAccountIds;

            }
        }
    }
}
