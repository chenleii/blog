package com.chen.blog.infrastructure.persistence.repository.domainconverter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author cl
 * @version 1.0
 * @since 2021/7/1 18:35
 */
@Mapper(componentModel = "default")
public interface EnumMapper {
    EnumMapper MAPPER = Mappers.getMapper(EnumMapper.class);



}
