package com.spavlenko.service;

import com.spavlenko.domain.User;
import com.spavlenko.exception.EntityNotFoundException;

/**
 * User service
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 */
public interface UserService {

    /**
     * Gets user by id
     * 
     * @param userId
     *            user id
     * @return user
     * @throws EntityNotFoundException
     *             when user is not found
     */
    User find(Long userId) throws EntityNotFoundException;

}
