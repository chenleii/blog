package com.chen.blog.start;

import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import com.chen.blog.core.sharedkernel.converter.Converter;
import com.chen.blog.interfaces.http.handler.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 针对已知graalvm编译&运行的错误处理
 * <p>
 * 该接口{@link RuntimeHintsRegistrar}是spring-aot用于特殊情况下生成graalvm元数据的一个抽象
 *
 * @author cl
 * @version 1.0
 * @since 2023/2/27 01:11
 */
public class BlogRuntimeHintsRegistrar implements RuntimeHintsRegistrar {
    @SneakyThrows
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.reflection().registerType(ExceptionHandler.class, MemberCategory.INVOKE_DECLARED_METHODS);

        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "com/chen/blog/**/*.class");
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        for (Resource resource : resources) {
            try {
                MetadataReader metadataReader = readerFactory.getMetadataReader(resource);
                // 判断是否为具体的类
                if (metadataReader.getClassMetadata().isConcrete()) {
                    String className = metadataReader.getClassMetadata().getClassName();
                    Class<?> aClass = Class.forName(className, false, this.getClass().getClassLoader());

                    // 判断是否为 Converter 的实现类
                    // 将 com.chen.blog.core.sharedkernel.converter.Converter 子类添加到反射
                    // mapstruct 没有将注解为 @Mapper 的实现类添加到 graalvm 元数据反射配置中，这里暂时自行实现一下。
                    if (Converter.class.isAssignableFrom(aClass)) {
                        hints.reflection().registerType(aClass, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
                    }

                    // 为 @Aspect 形式的aop生效
                    if (aClass.isAnnotationPresent(Aspect.class)) {
                        hints.reflection().registerType(aClass, MemberCategory.INVOKE_DECLARED_METHODS);
                    }
                }
            } catch (Exception e) {
                // 忽略
            }
        }
        // 避免es错误
        hints.reflection().registerType(IndexSettings.class, MemberCategory.DECLARED_FIELDS);
        hints.reflection().registerType(TypeMapping.class, MemberCategory.DECLARED_FIELDS);
        // 避免swagger错误
        hints.reflection().registerType(MethodParameter.class, MemberCategory.DECLARED_FIELDS);

        // 避免 HttpTraceMonitorLogAspectj 初始化报错
        hints.reflection().registerType(GetMapping.class, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
        hints.reflection().registerType(PostMapping.class, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
        hints.reflection().registerType(PutMapping.class, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
        hints.reflection().registerType(DeleteMapping.class, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
        hints.reflection().registerType(PatchMapping.class, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
        hints.reflection().registerType(GetMapping.class, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);


        hints.proxies().registerJdkProxy(HttpServletRequest.class);
        hints.proxies().registerJdkProxy(HttpServletResponse.class);

        hints.resources().registerPattern("iptoregion/**");
        hints.resources().registerPattern("ui/**");
        hints.resources().registerPattern("org/aspectj/weaver/**.properties");
    }
}
