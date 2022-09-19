package com.chen.blog.core.sharedkernel.serializer;


import com.chen.blog.core.sharedkernel.serializer.json.JacksonJsonSerializer;
import com.chen.blog.core.sharedkernel.serializer.json.JsonSerializer;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/8 22:40
 */
@Named
public final class Serializers {

    private static final JsonSerializer JSON_SERIALIZER = new JacksonJsonSerializer();

    private static Serializer serializer;

    @Inject
    public Serializers(Serializer serializer) {
        Serializers.serializer = serializer;
    }

    public static Serializer current() {
        return serializer;
    }

    public static JsonSerializer json() {
        return JSON_SERIALIZER;
    }

}
