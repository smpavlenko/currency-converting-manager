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

}
