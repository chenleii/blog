package com.chen.blog.core.sharedkernel.logger;

import com.chen.blog.core.sharedkernel.event.DomainEvent;
import com.chen.blog.core.sharedkernel.mq.Message;
import com.chen.blog.core.sharedkernel.serializer.Serializers;
import com.chen.blog.core.sharedkernel.tracer.Tracers;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author cl
 * @version 1.0
 * @since 2021/7/8 19:36
 */
public final class Loggers {

    /**
     * 分隔符
     */
    private static final String DELIMITER = "\u0001";

    /**
     * 获取时间戳
     *
     * @return 时间戳
     */
    private static long getTimestamp() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 业务监控日志
     */
    public static class BizMonitorLogger {
        private static final Logger logger = LoggerFactory.getLogger(BizMonitorLogger.class.getSimpleName());

        public static void log(String tag, Object... args) {
            if (ArrayUtils.isEmpty(args)) {
                logger.info(
                        DELIMITER
                                + tag
                                + DELIMITER
                );
                return;
            }
            logger.info(
                    DELIMITER
                            + tag
                            + DELIMITER
                            + Arrays.stream(args)
                            .map(Object::toString)
                            .collect(Collectors.joining(DELIMITER))
                            + DELIMITER
            );
        }
    }

    /**
     * 事件总线日志记录器
     */
    public static class EventBusLogger {
        private static final Logger logger = LoggerFactory.getLogger(EventBusLogger.class.getSimpleName());

        public static void log(DomainEvent domainEvent) {
            logger.info(
                    DELIMITER
                            + EventBusLogger.class.getSimpleName()
                            + DELIMITER
                            + Tracers.getTraceId()
                            + DELIMITER
                            + getTimestamp()
                            + DELIMITER
                            + domainEvent.code()
                            + DELIMITER
                            + domainEvent.type()
                            + DELIMITER
                            + domainEvent.occurredAt().toEpochMilli()
                            + DELIMITER
                            + Serializers.json().toJsonString(domainEvent)
                            + DELIMITER
            );

        }
    }

    /**
     * 消息队列日志记录器
     */
    public static class MessageQueueLogger {
        private static final Logger logger = LoggerFactory.getLogger(MessageQueueLogger.class.getSimpleName());

        public static void log(String topic, String tag, Message message) {
            logger.info(
                    DELIMITER
                            + MessageQueueLogger.class.getSimpleName()
                            + DELIMITER
                            + Tracers.getTraceId()
                            + DELIMITER
                            + getTimestamp()
                            + DELIMITER
                            + topic
                            + DELIMITER
                            + tag
                            + DELIMITER
                            + message.key()
                            + DELIMITER
                            + message.occurredAt().toEpochMilli()
                            + DELIMITER
                            + Serializers.json().toJsonString(message)
                            + DELIMITER
            );
        }
    }

}
