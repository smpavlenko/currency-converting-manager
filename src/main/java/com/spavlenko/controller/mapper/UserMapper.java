package com.spavlenko.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.spavlenko.domain.User;
import com.spavlenko.dto.UserDto;

/**
 * User Mapper
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * User -> UserDto mapping
     */
    @Mapping(target = "userName", source = "username")
    UserDto toUserDto(User user);

    /**
     * UserDto -> User mapping
     */
    @Mapping(target = "username", source = "userName")
    @Mapping(target = "dateCreated", ignore = false)
    User toUser(UserDto userDto);

}
