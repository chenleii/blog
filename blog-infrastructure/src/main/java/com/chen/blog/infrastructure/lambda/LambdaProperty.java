package com.chen.blog.infrastructure.lambda;

import java.util.Locale;
import java.util.Objects;

/**
 * lambda属性提取
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/11 19:05
 */
public class LambdaProperty {

    public static <T, R> String getProperty(LambdaFunction<T, R> lambdaFunction) {
        if (Objects.isNull(lambdaFunction)) {
            return null;
        }
        LambdaMeta lambdaMeta = Lambdas.parse(lambdaFunction);
        if (Objects.isNull(lambdaMeta)) {
            return null;
        }
        return methodNameToPropertyName(lambdaMeta.getImplMethodName());
    }

    private static String methodNameToPropertyName(final String methodName) {
        if (Objects.isNull(methodName)) {
            return null;
        }

        String tempMethodName = methodName;
        if (tempMethodName.startsWith("is")) {
            tempMethodName = tempMethodName.substring(2);
        } else if (tempMethodName.startsWith("get") || tempMethodName.startsWith("set")) {
            tempMethodName = tempMethodName.substring(3);
        }

        if (tempMethodName.length() >= 1 && Character.isUpperCase(tempMethodName.charAt(0))) {
            tempMethodName = tempMethodName.substring(0, 1).toLowerCase(Locale.ENGLISH) + tempMethodName.substring(1);
        }

        return tempMethodName;
    }
}
