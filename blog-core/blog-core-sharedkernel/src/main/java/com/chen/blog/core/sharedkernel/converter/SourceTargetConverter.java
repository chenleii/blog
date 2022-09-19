package com.chen.blog.core.sharedkernel.converter;

/**
 * @author cl
 * @version 1.0
 * @since 2020/10/29
 */
public interface SourceTargetConverter<Source, Target>
        extends SourceToTargetConverter<Source, Target>, SourceFromTargetConverter<Source, Target>, Converter {

}
