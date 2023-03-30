package com.chen.blog.infrastructure.persistence.repository.domainconverter;

import com.chen.blog.core.sharedkernel.converter.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.Objects;

/**
 * @author cl
 * @version 1.0
 * @since 2021/7/1 18:35
 */
@Mapper(componentModel = MappingConstants.ComponentModel.DEFAULT)
public interface TimeMapper extends Converter {
    TimeMapper MAPPER = Mappers.getMapper(TimeMapper.class);

    default Long map(Instant instant) {
        if (Objects.isNull(instant)) {
            return null;
        }
        return instant.toEpochMilli();
    }

    default Instant map(Long l) {
        if (Objects.isNull(l)) {
            return null;
        }
        return Instant.ofEpochMilli(l);
    }

}
