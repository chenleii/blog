package com.chen.blog.infrastructure.lambda;

import java.io.Serializable;

/**
 * 仅用于lambda表达式解析
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/11 19:05
 */
public interface LambdaFunction<T, R> extends Serializable {

    R function(T t);
}