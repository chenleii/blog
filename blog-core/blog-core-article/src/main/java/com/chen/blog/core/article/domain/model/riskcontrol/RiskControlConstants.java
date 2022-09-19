package com.chen.blog.core.article.domain.model.riskcontrol;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/27 16:59
 */
public class RiskControlConstants {

    /**
     * 违规词
     */
    public static final Set<String> SENSITIVE_WORDS = Sets.newHashSet(
            "脏话",
            "谩骂",
            "侮辱"
    );
}
