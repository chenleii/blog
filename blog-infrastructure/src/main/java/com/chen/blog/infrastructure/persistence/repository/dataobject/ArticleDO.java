package com.chen.blog.infrastructure.persistence.repository.dataobject;


import com.chen.blog.core.article.domain.model.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 *
 * @author cl
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@org.springframework.data.mongodb.core.mapping.Document(collection = "article")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "article")
public class ArticleDO implements Serializable {


    /**
     * id
     */
    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
    private Long id;

    /**
     * 账户ID
     */
    @Indexed
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
    private Long accountId;

    /**
     * 标题
     */
    @org.springframework.data.elasticsearch.annotations.MultiField(
            mainField = @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
            otherFields = {
                    @org.springframework.data.elasticsearch.annotations.InnerField(suffix = "pinyin", type = FieldType.Text, analyzer = "pinyin", searchAnalyzer = "pinyin")
            }
    )
    private String title;
    /**
     * 标签
     */
    @org.springframework.data.elasticsearch.annotations.MultiField(
            mainField = @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
            otherFields = {
                    @org.springframework.data.elasticsearch.annotations.InnerField(suffix = "pinyin", type = FieldType.Text, analyzer = "pinyin", searchAnalyzer = "pinyin")
            }
    )
    private Set<String> tags;
    /**
     * 内容
     */
    @org.springframework.data.elasticsearch.annotations.MultiField(
            mainField = @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
            otherFields = {
                    @org.springframework.data.elasticsearch.annotations.InnerField(suffix = "pinyin", type = FieldType.Text, analyzer = "pinyin", searchAnalyzer = "pinyin")
            }
    )
    private String content;

    /**
     * 状态
     */
    @Indexed
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private ArticleStatus status;
    /**
     * 喜欢记录
     */
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Nested)
    private List<ArticleLikeRecordDO> likes;
    /**
     * 举报记录
     */
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Nested)
    private List<ArticleReportRecordDO> reports;

    /**
     * 评论
     */
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Nested)
    private Set<ArticleCommentDO> comments;

    /**
     * 创建于
     */
    @Indexed
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
    private Long createdAt;
    /**
     * 更新于
     */
    @Indexed
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
    private Long updatedAt;

    @Data
    public static class ArticleLikeRecordDO implements Serializable {

        /**
         * 账户
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
        private Long accountId;

        /**
         * 是否喜欢
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Boolean)
        private Boolean isLiked;

        /**
         * 喜欢于
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
        private Long likedAt;

    }

    @Data
    public static class ArticleReportRecordDO implements Serializable {

        /**
         * 账户
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
        private Long accountId;

        /**
         * 备注
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
        private String remark;

        /**
         * 举报于
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
        private Long reportedAt;

    }

    @Data
    @EqualsAndHashCode(of = "id")
    public static class ArticleCommentDO implements Serializable {

        /**
         * 评论ID
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
        private Long id;
        /**
         * 评论的账户ID
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
        private Long accountId;

        /**
         * 评论内容
         */
        @org.springframework.data.elasticsearch.annotations.MultiField(
                mainField = @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
                otherFields = {
                        @org.springframework.data.elasticsearch.annotations.InnerField(suffix = "pinyin", type = FieldType.Text, analyzer = "pinyin", searchAnalyzer = "pinyin")
                }
        )
        private String content;
        /**
         * 评论于
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
        private Long commentedAt;

        /**
         * 子评论
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Nested)
        private Set<ArticleSubCommentDO> subComments;



//        private AccountDO account;
    }

    @Data
    @EqualsAndHashCode(of = "id")
    public static class ArticleSubCommentDO implements Serializable {

        /**
         * 子评论ID
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
        private Long id;
        /**
         * 回复的子评论ID
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
        private Long replyId;
        /**
         * 评论的账户ID
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
        private Long accountId;

        /**
         * 评论内容
         */
        @org.springframework.data.elasticsearch.annotations.MultiField(
                mainField = @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
                otherFields = {
                        @org.springframework.data.elasticsearch.annotations.InnerField(suffix = "pinyin", type = FieldType.Text, analyzer = "pinyin", searchAnalyzer = "pinyin")
                }
        )
        private String content;
        /**
         * 评论于
         */
        @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
        private Long commentedAt;




//        private AccountDO account;
    }






//
//    private AccountDO account;
//
//
//    /**
//     * 已喜欢的数量
//     */
//    private Integer likedNumber;
//
//    /**
//     * 已举报的数量
//     */
//    private Integer reportedNumber;
//    /**
//     * 是否已喜欢
//     */
//    private Boolean isLiked;
//
//    /**
//     * 是否已举报
//     */
//    private Boolean isReported;
//
//    /**
//     * 自定义描述
//     * <p>
//     * 尽量包含搜索关键词的内容
//     */
//    private String customContent;
//
//    /**
//     * 封面
//     * <p>
//     * 内容里面的第一个图片
//     */
//    private String cover;
}
