package com.chen.blog.infrastructure.lambda;

import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/11 19:05
 */
public class Lambdas {

    public static <T, R> LambdaMeta parse(LambdaFunction<T, R> lambda) {
        if (Objects.isNull(lambda)) {
            return null;
        }

        // 代理方式解析
        if (lambda instanceof Proxy) {
            return ProxyLambdaMetaParser.parse((Proxy) lambda);
        }

        Class<?> lambdaClass = lambda.getClass();
        // 粗过滤
        if (!lambdaClass.isSynthetic()) {
            throw new IllegalArgumentException("is not lambda class.");
        }

        try {
            // 反射方式解析
            return ReflectionLambdaMetaParser.parse(lambda);
        } catch (Throwable e) {
            // 序列化方式解析
            return SerializationLambdaMetaParser.parse(lambda);
        }
    }


}
