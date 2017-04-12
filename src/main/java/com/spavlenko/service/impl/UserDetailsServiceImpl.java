package com.spavlenko.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spavlenko.dao.UserRepository;

/**
 * Default implementation of UserDetailsService
 * 
 * @author sergii.pavlenko
 * @since April 11, 2017
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.spavlenko.domain.User user = userRepository.findByUsername(username);

        return new User(user.getUsername(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("SUPER_USER")));
    }

}
