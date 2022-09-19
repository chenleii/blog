package com.chen.blog.core.article.domain.model;

import com.chen.blog.core.account.domain.model.Account;
import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.sharedkernel.ddd.annotation.Entity;
import com.google.common.base.Preconditions;
import lombok.*;

import java.time.Instant;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 19:44
 */
@Entity
@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public class ArticleLikedRecord {

    /**
     * 账户
     */
    private AccountId accountId;

    /**
     * 是否喜欢
     */
    private Boolean isLiked;

    /**
     * 喜欢于
     */
    private Instant likedAt;

    public static ArticleLikedRecord create(Account account, boolean isLiked) {
        Preconditions.checkNotNull(account);
        Preconditions.checkNotNull(isLiked);

        return ArticleLikedRecord.builder()
                .accountId(account.getId())
                .isLiked(isLiked)
                .likedAt(Instant.now())
                .build();
    }
}
