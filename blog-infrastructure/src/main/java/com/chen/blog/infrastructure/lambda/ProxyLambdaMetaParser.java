package com.chen.blog.infrastructure.lambda;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/11 20:00
 */
class ProxyLambdaMetaParser {

    private static final Field DIRECT_METHOD_HANDLE_MEMBER_FIELD;
    private static final Field MEMBER_NAME_CLAZZ_FIELD;
    private static final Field MEMBER_NAME_NAME_FIELD;

    static {
        try {
            Class<?> directMethodHandleClass = ClassUtils.getClass("java.lang.invoke.DirectMethodHandle");
            DIRECT_METHOD_HANDLE_MEMBER_FIELD = FieldUtils.getDeclaredField(directMethodHandleClass, "member", true);
            Class<?> memberNameClass = ClassUtils.getClass("java.lang.invoke.MemberName");
            MEMBER_NAME_CLAZZ_FIELD = FieldUtils.getDeclaredField(memberNameClass, "clazz", true);
            MEMBER_NAME_NAME_FIELD = FieldUtils.getDeclaredField(memberNameClass, "name", true);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    static LambdaMeta parse(Proxy lambda) {
        InvocationHandler handler = Proxy.getInvocationHandler(lambda);

        Object dmh = FieldUtils.readField(handler, "val$target", true);
        Object member = DIRECT_METHOD_HANDLE_MEMBER_FIELD.get(dmh);
        Class<?> implClass = (Class<?>) MEMBER_NAME_CLAZZ_FIELD.get(member);
        String implMethodName = (String) MEMBER_NAME_NAME_FIELD.get(member);

        return LambdaMeta.of(implClass,implMethodName);
    }
}
