package com.chen.blog.infrastructure.lambda;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/11 20:00
 */
class ReflectionLambdaMetaParser extends LambdaMeta {

    @SneakyThrows
    static LambdaMeta parse(Object lambda) {
        java.lang.invoke.SerializedLambda serializedLambda = (java.lang.invoke.SerializedLambda) MethodUtils.invokeMethod(lambda, true, "writeReplace");
        String implClass = serializedLambda.getImplClass();
        implClass = implClass.replace('/', '.');
        return LambdaMeta.of(ClassUtils.getClass(implClass), serializedLambda.getImplMethodName());
    }

}
