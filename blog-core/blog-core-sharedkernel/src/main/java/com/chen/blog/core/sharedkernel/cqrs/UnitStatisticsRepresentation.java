package com.chen.blog.core.sharedkernel.cqrs;

import lombok.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

/**
 * 单位时间统计
 *
 * @author cl
 * @version 1.0
 * @since 2021/7/7 10:12
 */
@Setter
@Getter
@ToString
@AllArgsConstructor(staticName = "of")
public class UnitStatisticsRepresentation implements Representation {

    /**
     * 时间单位
     */
    private ChronoUnit unit = ChronoUnit.DAYS;

    /**
     * 分段列表
     */
    private List<Segment> segments;

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @EqualsAndHashCode(of = "key")
    public static class Segment implements Representation {
        /**
         * 分段标识
         * <p>
         * 如:
         * 天:yyyyMMdd
         * 小时:yyyyMMddHH
         * 分钟:yyyyMMddHHmm
         */
        private String key;

        /**
         * 分段对应的值
         */
        private BigDecimal value;
    }

}
