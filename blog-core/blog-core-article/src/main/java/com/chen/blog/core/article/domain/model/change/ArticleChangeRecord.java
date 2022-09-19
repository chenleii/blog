package com.chen.blog.core.article.domain.model.change;

import com.chen.blog.core.sharedkernel.ddd.annotation.Entity;
import lombok.*;

import java.time.Instant;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 19:48
 */
@Entity
@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public class ArticleChangeRecord {

    /**
     * 变更类型
     */
    private ArticleChangeType type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 快照
     */
    private ArticleSnapshot snapshot;

    /**
     * 修改于
     */
    private Instant changedAt;
}
