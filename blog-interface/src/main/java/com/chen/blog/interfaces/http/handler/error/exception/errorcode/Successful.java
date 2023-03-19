package com.chen.blog.interfaces.http.handler.error.exception.errorcode;

/**
 * 没啥用，当做默认成功编码。
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/3 15:52
 */
public class Successful extends RuntimeException {

    private Successful() {
        throw new RuntimeException();
    }

}
