package com.pixel.demo.mapper;

import com.pixel.demo.dto.AccountResDto;
import com.pixel.demo.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {

    @Mapping(source = "user.id", target = "userId")
    AccountResDto toDto(Account account);
}
