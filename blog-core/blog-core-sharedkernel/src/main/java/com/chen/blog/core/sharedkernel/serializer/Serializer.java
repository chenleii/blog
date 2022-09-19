package com.chen.blog.core.sharedkernel.serializer;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/8 22:40
 */
public interface Serializer {


    /**
     * 对象序列化为字节数组
     *
     * @param source 源对象
     * @return 字节数组
     */
    byte[] toBytes(Object source);


    /**
     * 从字节数组反序列化为对象
     *
     * @param bytes      源字节数组
     * @param targetType 目标对象类型
     * @param <T>        目标对象类型泛型
     * @return 目标对象
     */
    <T> T fromBytes(byte[] bytes, Class<T> targetType);

    /**
     * 从字节数组反序列化为对象
     *
     * @param bytes         源字节数组
     * @param targetType 目标对象类型
     * @param <T>           目标对象类型泛型
     * @return 目标对象
     */
    <T> T fromBytes(byte[] bytes, TypeReference<T> targetType);


    /**
     * 对象序列化为字符串
     *
     * @param source 源对象
     * @return 字符串
     */
    default String toString(Object source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return new String(toBytes(source), StandardCharsets.UTF_8);
    }

    /**
     * 从字符串反序列化为对象
     *
     * @param source     字符串
     * @param targetType 目标对象类型
     * @param <T>        目标对象类型泛型
     * @return 目标对象
     */
    default <T> T fromString(String source, Class<T> targetType) {
        if (Objects.isNull(source)) {
            return null;
        }
        byte[] bytes = source.getBytes(StandardCharsets.UTF_8);
        return fromBytes(bytes, targetType);
    }

    /**
     * 从字符串反序列化为对象
     *
     * @param source        字符串
     * @param targetType 目标对象类型
     * @param <T>           目标对象类型泛型
     * @return 目标对象
     */
    default <T> T fromString(String source, TypeReference<T> targetType) {
        if (Objects.isNull(source)) {
            return null;
        }
        byte[] bytes = source.getBytes(StandardCharsets.UTF_8);
        return fromBytes(bytes, targetType);
    }

    /**
     * 从字节数组反序列化为对象列表
     *
     * @param bytes             源字节数组
     * @param targetElementType 目标对象元素类型
     * @param <T>               目标对象类型泛型
     * @return 目标对象列表
     */
    default <T> List<T> fromBytesToList(byte[] bytes, Class<T> targetElementType) {
        if (Objects.isNull(bytes)) {
            return Collections.emptyList();
        }
        return fromBytes(bytes, new TypeReference<List<T>>() {
        });
    }

    /**
     * 从字符串反序列化为对象列表
     *
     * @param source            字符串
     * @param targetElementType 目标对象元素类型
     * @param <T>               目标对象类型泛型
     * @return 目标对象列表
     */
    default <T> List<T> fromStringToList(String source, Class<T> targetElementType) {
        if (Objects.isNull(source)) {
            return Collections.emptyList();
        }
        byte[] bytes = source.getBytes(StandardCharsets.UTF_8);
        return fromBytes(bytes, new TypeReference<List<T>>() {
        });
    }


    /**
     * 用于解析泛型
     */
    abstract class TypeReference<T> {
        protected final Type type;

        protected TypeReference() {
            Type superClass = getClass().getGenericSuperclass();
            if (superClass instanceof Class<?>) {
                // sanity check, should never happen
                throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
            }

            type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        }

        public Type getType() {
            return type;
        }

    }
}
