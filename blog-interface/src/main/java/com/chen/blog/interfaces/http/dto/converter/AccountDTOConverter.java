package com.chen.blog.interfaces.http.dto.converter;

import com.chen.blog.core.account.domain.model.cqrs.representation.AccountRepresentation;
import com.chen.blog.core.sharedkernel.converter.SourceToTargetConverter;
import com.chen.blog.interfaces.http.dto.output.AccountOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default")
public interface AccountDTOConverter extends SourceToTargetConverter<AccountRepresentation, AccountOutputDTO> {
    AccountDTOConverter MAPPER = Mappers.getMapper(AccountDTOConverter.class);


}