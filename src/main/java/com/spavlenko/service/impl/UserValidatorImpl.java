package com.spavlenko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.spavlenko.dto.UserDto;
import com.spavlenko.service.UserService;
import com.spavlenko.service.UserValidator;

/**
 * User validator implementation
 * 
 * @author sergii.pavlenko
 * @since Apr 16, 2017
 */
@Service
public class UserValidatorImpl implements UserValidator {

    @Autowired
    private UserService userService;

    @Override
    public void validate(UserDto user, String passwordConfirm, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");

        if (user.getUserName().length() < 4) {
            errors.rejectValue("userName", "Size.userForm.username");
        }
        if (userService.find(user.getUserName()) != null) {
            errors.rejectValue("userName", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!passwordConfirm.equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }

    }

}
