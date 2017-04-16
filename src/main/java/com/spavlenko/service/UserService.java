package com.spavlenko.service;

import com.spavlenko.domain.User;
import com.spavlenko.exception.ConstraintsViolationException;
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

    /**
     * Gets user by user name
     * 
     * @param userName
     *            user name
     * @return user (null if user not found)
     */
    User find(String userName);

    /**
     * Creates new user
     * 
     * @param user
     *            user to create
     * @return user
     * @throws ConstraintsViolationException
     *             when some constraints are violated
     */
    User create(User user) throws ConstraintsViolationException;
}
