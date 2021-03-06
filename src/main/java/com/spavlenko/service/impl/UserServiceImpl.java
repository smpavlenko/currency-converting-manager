package com.spavlenko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.spavlenko.dao.UserRepository;
import com.spavlenko.domain.User;
import com.spavlenko.exception.ConstraintsViolationException;
import com.spavlenko.exception.EntityNotFoundException;
import com.spavlenko.service.UserService;

/**
 * Default user service implementation
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User find(Long userId) throws EntityNotFoundException {
        User userDO = userRepository.findOne(userId);
        if (userDO == null) {
            throw new EntityNotFoundException("Could not find user with id: " + userId);
        }
        return userDO;
    }

    @Override
    public User create(User user) throws ConstraintsViolationException {

        User createdPlayer = null;
        try {
            createdPlayer = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintsViolationException(e.getMessage());
        }
        return createdPlayer;
    }

    @Override
    public User find(String userName) {
        return userRepository.findByUsername(userName);
    }

}
