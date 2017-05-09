package com.spavlenko.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.spavlenko.controller.request.UserRequest;
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
     * UserRequest -> UserDto mapping
     */
    UserDto toUserDto(UserRequest userRequest);

    /**
     * UserDto -> User mapping
     */
    @Mapping(target = "username", source = "userName")
    User toUser(UserDto userDto);

    /**
     * UserRequest -> User mapping
     */
    @Mapping(target = "username", source = "userName")
    User toUser(UserRequest userRequest);

}
