package com.spavlenko.dao;

import org.springframework.data.repository.CrudRepository;

import com.spavlenko.domain.User;

/**
 * User repository
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Gets user by user name
     * 
     * @param username
     *            user name
     * @return found User
     */
    User findByUsername(String username);

}
