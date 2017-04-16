package com.spavlenko.service;

import com.spavlenko.domain.User;
import com.spavlenko.exception.EntityNotFoundException;

/**
 * Security service for security purposes.
 * 
 * @author sergii.pavlenko
 * @since Apr 16, 2017
 */
public interface SecurityService {

    /**
     * Returns authenticated user
     * 
     * @return authenticated user
     * @throws EntityNotFoundException
     *             if user not found
     */
    User getAuthenticatedUser() throws EntityNotFoundException;

    /**
     * Logs in an user
     * 
     * @param userName
     *            user name
     * @param password
     *            user password
     * @throws EntityNotFoundException
     */
    void login(String userName, String password);
}
