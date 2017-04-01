package com.spavlenko.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spavlenko.CurrencyConverterApplication;
import com.spavlenko.dto.UserDto;

/**
 * Acceptance tests for UserController
 *
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CurrencyConverterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerAcceptanceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void findById() throws Exception {
        // when
        UserDto resultUser = this.restTemplate.getForObject("/v1/users/1", UserDto.class);

        // then
        assertThat(resultUser.getId(), equalTo(1L));
        assertThat(resultUser.getUserName(), equalTo("test1"));
        assertThat(resultUser.getPassword(), equalTo("test1"));
    }

    @Test
    public void findById_exception() throws Exception {
        // when
        String body = this.restTemplate.getForObject("/v1/users/100", String.class);

        // then
        JSONObject json = new JSONObject(body);
        assertThat(json.get("status"), equalTo(400));
        assertThat(json.get("exception"), equalTo("com.spavlenko.exception.EntityNotFoundException"));
    }

}
