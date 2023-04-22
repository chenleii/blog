package com.chen.blog.core.notification.domain.model.cqrs.representation;

import com.chen.blog.core.notification.domain.model.NotificationDetailsType;
import com.chen.blog.core.notification.domain.model.NotificationStatus;
import com.chen.blog.core.sharedkernel.cqrs.Representation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Indexed;

import java.io.Serializable;
import java.util.List;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/6 22:23
 */
@Getter
@Setter
@ToString
public class NotificationRepresentation implements Representation {


    /**
     * ID
     */
    private Long id;


    /**
     * 账户ID
     */
    private Long accountId;
    /**
     * 详情
     */
    private NotificationDetails details;

    /**
     * 状态
     */
    private NotificationStatus status;


    /**
     * 创建于
     */
    private Long createdAt;
    /**
     * 已读于
     */
    private Long hasReadAt;
    /**
     * 清除于
     */
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
