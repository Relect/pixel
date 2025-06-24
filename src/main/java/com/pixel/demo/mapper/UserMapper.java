package com.pixel.demo.mapper;

import com.pixel.demo.dto.UserResDto;
import com.pixel.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {AccountMapper.class, EmailMapper.class, PhoneMapper.class})
public interface UserMapper {

    UserResDto toDto(User user);
}
