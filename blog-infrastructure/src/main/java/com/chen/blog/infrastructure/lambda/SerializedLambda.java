package com.chen.blog.infrastructure.lambda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * copy {@link java.lang.invoke.SerializedLambda }
 * <p>
 * 必须同名
 * 用于序列化并反序列化解析lambda表达式。
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public final class SerializedLambda implements Serializable {
    private static final long serialVersionUID = 8025925345765570181L;
    private final Class<?> capturingClass;
    private final String functionalInterfaceClass;
    private final String functionalInterfaceMethodName;
    private final String functionalInterfaceMethodSignature;
    private final String implClass;
    private final String implMethodName;
    private final String implMethodSignature;
    private final int implMethodKind;
    private final String instantiatedMethodType;
    private final Object[] capturedArgs;
}