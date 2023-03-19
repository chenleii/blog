package com.chen.blog.interfaces.http.handler.error.exception.errorcode;

/**
 * 没啥用，当做默认错误编码。
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/3 15:52
 */
public class UnknownError extends RuntimeException {

    private UnknownError() {
        throw new RuntimeException();
    }

}
