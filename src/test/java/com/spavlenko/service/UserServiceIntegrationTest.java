package com.spavlenko.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spavlenko.CurrencyConverterApplication;
import com.spavlenko.domain.User;
import com.spavlenko.exception.EntityNotFoundException;

/**
 * Integration tests for UserService
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CurrencyConverterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void findById() throws Exception {
        // when
        User resultUser = userService.find(1L);

        // then
        assertThat(resultUser.getId(), equalTo(1L));
        assertThat(resultUser.getUsername(), equalTo("test1"));
        assertThat(resultUser.getPassword(), equalTo("test1"));
    }

    @Test
    public void findById_exception() throws Exception {
        // given
        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage("Could not find user with id: 1000");

        // when
        userService.find(1000L);
    }

}
