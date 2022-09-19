package com.chen.blog.core.article.domain.model.change;

import com.chen.blog.core.sharedkernel.ddd.annotation.ValueObject;
import lombok.Getter;
import lombok.ToString;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 19:49
 */
@ValueObject
@Getter
@ToString
public enum ArticleChangeType {
    /**
     * 保存
     */
    SAVED,
    /**
     * 发布
     */
    PUBLICATION,

    /**
     * 违规不可见
     */
    INVISIBLE,



    ;

}
