package com.spavlenko.service;

import org.springframework.validation.Errors;

import com.spavlenko.dto.UserDto;

/**
 * User validator
 * 
 * @author sergii.pavlenko
 * @since Apr 16, 2017
 */
public interface UserValidator {

    /**
     * Validates user. Adds errors to errors attribute
     * 
     * @param user
     *            user dto
     * @param passwordConfirm
     *            password confirm
     * @param errors
     *            errors attribute
     */
    void validate(UserDto user, String passwordConfirm, Errors errors);
}
