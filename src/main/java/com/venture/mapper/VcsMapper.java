package com.venture.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.venture.dtos.UserDto;
import com.venture.entitys.User;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface VcsMapper {

	User toUser(UserDto dto);

	UserDto toUserDto(User user);

}
