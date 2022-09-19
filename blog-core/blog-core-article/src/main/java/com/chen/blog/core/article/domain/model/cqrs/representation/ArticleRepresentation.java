package com.chen.blog.core.article.domain.model.cqrs.representation;

import com.chen.blog.core.account.domain.model.cqrs.representation.AccountRepresentation;
import com.chen.blog.core.sharedkernel.cqrs.Representation;
import com.chen.blog.core.article.domain.model.ArticleStatus;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:10
 */
@Getter
@Setter
@ToString
public class ArticleRepresentation implements Representation {

    /**
     * id
     */
    private Long id;

    /**
     * 账户ID
     */
    private Long accountId;

    /**
     * 标题
     */
    private String title;
    /**
     * 标签
     */
    private Set<String> tags;
    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private ArticleStatus status;
    /**
     * 喜欢记录
     */
    private List<ArticleLikeRecordDO> likes;
    /**
     * 举报记录
     */
    private List<ArticleReportRecordDO> reports;

    /**
     * 评论
     */
    private Set<ArticleCommentDO> comments;

    /**
     * 创建于
     */
    private Instant createdAt;
    /**
     * 更新于
     */
    private Instant updatedAt;


    private AccountRepresentation account;


    /**
     * 已喜欢的数量
     */
    private Integer likedNumber;

    /**
     * 已举报的数量
     */
    private Integer reportedNumber;
    /**
     * 是否已喜欢
     */
    private Boolean isLiked;

    /**
     * 是否已举报
     */
    private Boolean isReported;

    /**
     * 自定义描述
     * <p>
     * 尽量包含搜索关键词的内容
     */
    private String customContent;

    /**
     * 封面
     * <p>
     * 内容里面的第一个图片
     */
    private String cover;


    @Data
    public static class ArticleLikeRecordDO implements Serializable {

        /**
         * 账户
         */
        private Long accountId;

        /**
         * 是否喜欢
         */
        private Boolean isLiked;

        /**
         * 喜欢于
         */
        private Instant likedAt;

    }

    @Data
    public static class ArticleReportRecordDO implements Serializable {

        /**
         * 账户
         */
        private Long accountId;

        /**
         * 备注
         */
        private String remark;

        /**
         * 举报于
         */
        private Instant reportedAt;

    }


    @Data
    @EqualsAndHashCode(of = "id")
    public static class ArticleCommentDO implements Serializable {

        /**
         * 评论ID
         */
        private Long id;
        /**
         * 评论的账户ID
         */
        private Long accountId;

        /**
         * 评论内容
         */
        private String content;
        /**
         * 评论于
         */
        private Instant commentedAt;

        /**
         * 子评论
         */
        private Set<ArticleSubCommentDO> subComments;

        private AccountRepresentation account;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ArticleSubCommentDO extends ArticleCommentDO implements Serializable {

        /**
         * 回复的子评论ID
         */
        private Long replyId;

    }

}
