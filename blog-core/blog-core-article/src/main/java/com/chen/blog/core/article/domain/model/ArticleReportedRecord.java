package com.chen.blog.core.article.domain.model;

import com.chen.blog.core.account.domain.model.Account;
import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.sharedkernel.ddd.annotation.Entity;
import com.google.common.base.Preconditions;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

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
public class ArticleReportedRecord {

    /**
     * 账户
     */
    private AccountId accountId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 举报于
     */
    private Instant reportedAt;

    public static ArticleReportedRecord create(Account account, String remark) {
        Preconditions.checkNotNull(account);
        remark = ObjectUtils.defaultIfNull(remark, "");

        return ArticleReportedRecord.builder()
                .accountId(account.getId())
                .remark(remark)
                .reportedAt(Instant.now())
                .build();
    }
}
