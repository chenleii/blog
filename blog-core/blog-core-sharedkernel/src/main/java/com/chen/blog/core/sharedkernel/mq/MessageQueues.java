package com.chen.blog.core.sharedkernel.mq;

import com.chen.blog.core.sharedkernel.logger.Loggers;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Objects;

/**
 * @author cl
 * @version 1.0
 * @since 2021/7/22 12:18
 */
@Named
public class MessageQueues {
    private static MessageQueuePublisher messageQueuePublisher;

    @Inject
    public void setEventPublisher(MessageQueuePublisher messageQueuePublisher) {
        if (Objects.isNull(messageQueuePublisher)) {
            throw new IllegalArgumentException("messageQueuePublisher is null.");
        }
        MessageQueues.messageQueuePublisher = messageQueuePublisher;
    }

    public static void publish(String topic, String tag, Message message) {
        messageQueuePublisher.publish(topic, tag, message);

        Loggers.MessageQueueLogger.log(topic, tag, message);
    }
}
