package com.chen.blog.core.sharedkernel.serializer.json;

import com.chen.blog.core.sharedkernel.serializer.Serializer;

import java.util.List;

/**
 * @author cl
 * @version 1.0
 * @since 2020/10/29
 */
public interface JsonSerializer extends Serializer {


    @Override
    default byte[] toBytes(Object source) {
        return toJsonBytes(source);
    }

    @Override
    default <T> T fromBytes(byte[] bytes, Class<T> targetType) {
        return fromJsonBytes(bytes, targetType);
    }

    @Override
    default <T> T fromBytes(byte[] bytes, TypeReference<T> typeReference) {
        return fromJsonBytes(bytes, typeReference);
    }

    @Override
    default String toString(Object source) {
        return toJsonString(source);
    }

    @Override
    default <T> T fromString(String source, Class<T> targetType) {
        return fromJsonString(source, targetType);
    }

    @Override
    default <T> T fromString(String source, TypeReference<T> targetType) {
        return fromJsonString(source, targetType);
    }

    @Override
    default <T> List<T> fromBytesToList(byte[] bytes, Class<T> targetElementType) {
        return fromJsonBytesToList(bytes, targetElementType);
    }

    @Override
    default <T> List<T> fromStringToList(String source, Class<T> targetElementType) {
        return fromJsonStringToList(source, targetElementType);
    }

    /**
     * 对象序列化为json字节数组
     *
     * @param source 源对象
     * @return json字节数组
     */
    byte[] toJsonBytes(Object source);

    /**
     * 对象序列化为json字符串
     *
     * @param source 源对象
     * @return json字符串
     */
    String toJsonString(Object source);

    /**
     * 从json字节数组反序列化为对象
     *
     * @param jsonBytes  json字节数组
     * @param targetType 目标对象类型
     * @param <T>        目标对象类型泛型
     * @return 目标对象
     */
    <T> T fromJsonBytes(byte[] jsonBytes, Class<T> targetType);

    /**
     * 从json字节数组反序列化为对象
     *
     * @param jsonBytes     json字节数组
     * @param typeReference 目标对象类型
     * @param <T>           目标对象类型泛型
     * @return 目标对象
     */
    <T> T fromJsonBytes(byte[] jsonBytes, Serializer.TypeReference<T> typeReference);

    /**
     * 从json字符串反序列化为对象
     *
     * @param jsonString json字符串
     * @param targetType 目标对象类型
     * @param <T>        目标对象类型泛型
     * @return 目标对象
     */
    <T> T fromJsonString(String jsonString, Class<T> targetType);

    /**
     * 从json字符串反序列化为对象
     *
     * @param jsonString json字符串
     * @param targetType 目标对象类型
     * @param <T>        目标对象类型泛型
     * @return 目标对象
     */
    <T> T fromJsonString(String jsonString, Serializer.TypeReference<T> targetType);

    /**
     * 从json字节反序列化为对象列表
     *
     * @param jsonBytes  json字节数组
     * @param targetType 目标对象元素类型
     * @param <T>        目标对象元素类型泛型
     * @return 目标对象列表
     */
    <T> List<T> fromJsonBytesToList(byte[] jsonBytes, Class<T> targetType);

    /**
     * 从json字符串反序列化为对象列表
     *
     * @param jsonString json字符串
     * @param targetType 目标对象元素类型
     * @param <T>        目标对象元素类型泛型
     * @return 目标对象列表
     */
    <T> List<T> fromJsonStringToList(String jsonString, Class<T> targetType);
}
