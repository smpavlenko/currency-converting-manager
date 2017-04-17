package com.spavlenko.service;

import com.spavlenko.domain.User;

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
     */
    User getAuthenticatedUser();

    /**
     * Logs in an user
     * 
     * @param userName
     *            user name
     * @param password
     *            user password
     */
    void login(String userName, String password);
}
