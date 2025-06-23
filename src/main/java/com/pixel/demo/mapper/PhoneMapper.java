package com.pixel.demo.mapper;

import com.pixel.demo.dto.ResponsePhoneDto;
import com.pixel.demo.model.PhoneData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PhoneMapper {

    @Mapping(source = "user.id", target = "userId")
    ResponsePhoneDto toDto(PhoneData phoneData);
}
