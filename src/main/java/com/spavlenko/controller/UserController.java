package com.spavlenko.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.spavlenko.controller.mapper.UserMapper;
import com.spavlenko.domain.User;
import com.spavlenko.dto.UserDto;
import com.spavlenko.exception.ConstraintsViolationException;
import com.spavlenko.exception.EntityNotFoundException;
import com.spavlenko.service.UserService;

/**
 * User controller
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 *
 */
@RestController
@RequestMapping("v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    /**
     * Gets an information about particular user.
     * 
     * @param userId
     *            user id
     * @return user info
     * @throws EntityNotFoundException
     *             if user not found
     */
    @GetMapping("/{userId}")
    public UserDto getUser(@Valid @PathVariable long userId) throws EntityNotFoundException {
        User user = userService.find(userId);
        return userMapper.toUserDto(user);
    }

    /**
     * Creates new user.
     * 
     * @param userDto
     *            user to create
     * @return created user
     * @throws ConstraintsViolationException
     *             if some constraints are failed
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) throws ConstraintsViolationException {
        // TODO to implement
        return null;
    }

}
