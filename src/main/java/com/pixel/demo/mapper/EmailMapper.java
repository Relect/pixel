package com.pixel.demo.mapper;

import com.pixel.demo.dto.EmaiResDto;
import com.pixel.demo.model.EmailData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmailMapper {

    @Mapping(source = "user.id", target = "userId")
    EmaiResDto toDto(EmailData emailData);
}
