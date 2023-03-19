package com.chen.blog.interfaces.http.handler.result;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 响应封装类
 *
 * @author cl
 * @version 1.0
 * @since 2020/11/23 00:07
 */
@Builder
@Getter
@ToString
@AllArgsConstructor
@Schema(description = "响应结果")
public class R<T> implements Serializable {

    /**
     * 链路跟踪ID
     */
    private final String traceId;

    /**
     * 是否成功
     */
    private final boolean success;
    /**
     * 错误码
     */
    private final String errorCode;
    /**
     * 错误信息
     */
    private final String errorMessage;
    /**
     * 错误堆栈跟踪
     */
    private final String errorStackTrace;

    /**
     * 错误
     * <p>
     * 具体错误信息
     */
    private final Object error;

    /**
     * 响应数据
     */
    private final T data;

    public static <T> R dataOf(T data) {
        return R.builder()
                .data(data)
                .build();
    }
}
