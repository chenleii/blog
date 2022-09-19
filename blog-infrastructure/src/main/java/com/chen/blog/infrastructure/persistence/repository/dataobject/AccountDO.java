package com.chen.blog.infrastructure.persistence.repository.dataobject;

import com.chen.blog.core.account.domain.model.AccountStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;

/**
 *
 * @author cl
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@org.springframework.data.mongodb.core.mapping.Document(collection = "account")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "account")
public class AccountDO implements Serializable {

    /**
     * id
     */
    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
    private Long id;

    /**
     * 头像
     */
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String avatar;
    /**
     * 名称
     */
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String name;
    /**
     * 手机号
     */
    @Indexed(unique = true)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String phoneNo;
    /**
     * 密码
     */
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String password;


    /**
     * 简介
     */
    @org.springframework.data.elasticsearch.annotations.MultiField(
            mainField = @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
            otherFields = {
                    @org.springframework.data.elasticsearch.annotations.InnerField(suffix = "pinyin", type = FieldType.Text, analyzer = "pinyin", searchAnalyzer = "pinyin")
            }
    )
    private String introduction;

    /**
     * 影响力
     */
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Long)
    private Integer influenceValue;

    /**
     * 状态
     */
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private AccountStatus status;

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


}
