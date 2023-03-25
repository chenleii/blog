package com.chen.blog.core.sharedkernel.serializer.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.zalando.jackson.datatype.money.MoneyModule;

import jakarta.inject.Named;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS;

/**
 * @author cl
 * @version 1.0
 * @since 2020/10/29
 */
@Named
public class JacksonJsonSerializer implements JsonSerializer {

    private static final JacksonJsonSerializer INSTANCE = new JacksonJsonSerializer();

    private final ObjectMapper objectMapper;

    public JacksonJsonSerializer() {
        this.objectMapper = new ObjectMapper();
        configure(objectMapper);
    }


    public static void configure(ObjectMapper configureObjectMapper) {
        // 忽略未知字段
        configureObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 时间转时间戳（毫秒）
        configureObjectMapper.configure(WRITE_DATES_AS_TIMESTAMPS, true);
        configureObjectMapper.configure(WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        configureObjectMapper.configure(READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        // jdk8配置
        configureObjectMapper.registerModule(new JavaTimeModule());
        configureObjectMapper.registerModule(new Jdk8Module());

        // 货币配置
        configureObjectMapper.registerModule(new MoneyModule());

        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(long.class, ToStringSerializer.instance);
        module.addSerializer(Long[].class, new com.fasterxml.jackson.databind.JsonSerializer<Long[]>() {
            @Override
            public final void serialize(
                    Long[] value, JsonGenerator g, SerializerProvider provider)
                    throws IOException {
                g.writeStartArray();
                for (long l : value) {
                    g.writeString(String.valueOf(l));
                }
                g.writeEndArray();
            }
        });
        module.addSerializer(long[].class, new com.fasterxml.jackson.databind.JsonSerializer<long[]>() {
            @Override
            public final void serialize(
                    long[] value, JsonGenerator g, SerializerProvider provider)
                    throws IOException {
                g.writeStartArray();
                for (long l : value) {
                    g.writeString(String.valueOf(l));
                }
                g.writeEndArray();
            }
        });
        configureObjectMapper.registerModule(module);
    }

    public static ObjectMapper getObjectMapper() {
        return INSTANCE.objectMapper;
    }


    @SneakyThrows
    @Override
    public byte[] toJsonBytes(Object source) {
        return getObjectMapper().writeValueAsBytes(source);
    }

    @SneakyThrows
    @Override
    public String toJsonString(Object source) {
        return getObjectMapper().writeValueAsString(source);
    }

    @SneakyThrows
    @Override
    public <T> T fromJsonBytes(byte[] jsonBytes, Class<T> targetType) {
        return getObjectMapper().readValue(jsonBytes, targetType);
    }

    @SneakyThrows
    @Override
    public <T> T fromJsonBytes(byte[] jsonBytes, TypeReference<T> typeReference) {
        return getObjectMapper().readValue(jsonBytes, new com.fasterxml.jackson.core.type.TypeReference<T>() {
        });
    }

    @SneakyThrows
    @Override
    public <T> T fromJsonString(String jsonString, Class<T> targetType) {
        return getObjectMapper().readValue(jsonString, targetType);
    }

    @SneakyThrows
    @Override
    public <T> T fromJsonString(String jsonString, TypeReference<T> targetType) {
        return getObjectMapper().readValue(jsonString, new com.fasterxml.jackson.core.type.TypeReference<T>() {
        });
    }

    @SneakyThrows
    @Override
    public <T> List<T> fromJsonBytesToList(byte[] jsonBytes, Class<T> targetType) {
        return getObjectMapper().readValue(jsonBytes, new com.fasterxml.jackson.core.type.TypeReference<List<T>>() {
        });
    }

    @SneakyThrows
    @Override
    public <T> List<T> fromJsonStringToList(String jsonString, Class<T> targetType) {
        return getObjectMapper().readValue(jsonString, new com.fasterxml.jackson.core.type.TypeReference<List<T>>() {
        });
    }
}