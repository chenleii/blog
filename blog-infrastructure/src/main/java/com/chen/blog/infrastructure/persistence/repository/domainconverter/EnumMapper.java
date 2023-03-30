package com.chen.blog.infrastructure.persistence.repository.domainconverter;

import com.chen.blog.core.sharedkernel.converter.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * @author cl
 * @version 1.0
 * @since 2021/7/1 18:35
 */
@Mapper(componentModel = MappingConstants.ComponentModel.DEFAULT)
public interface EnumMapper extends Converter {
    EnumMapper MAPPER = Mappers.getMapper(EnumMapper.class);



}
