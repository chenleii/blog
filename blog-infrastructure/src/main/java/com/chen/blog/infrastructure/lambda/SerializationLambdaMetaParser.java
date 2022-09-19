package com.chen.blog.infrastructure.lambda;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;

import java.io.*;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/11 20:00
 */
class SerializationLambdaMetaParser {

    @SneakyThrows
    static LambdaMeta parse(Serializable lambda) {
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
        ) {
            objectOutputStream.writeObject(lambda);
            objectOutputStream.flush();

            try (
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream) {
                        @Override
                        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                            Class<?> clazz = super.resolveClass(desc);
                            return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
                        }

                    }
            ) {

                SerializedLambda serializedLambda = (SerializedLambda) objectInputStream.readObject();
                String implClass = serializedLambda.getImplClass();
                implClass = implClass.replace('/', '.');
                return LambdaMeta.of(ClassUtils.getClass(implClass), serializedLambda.getImplMethodName());

            }
        }
    }
}
