package com.spavlenko.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spavlenko.CurrencyConverterApplication;
import com.spavlenko.dto.UserDto;
import com.spavlenko.service.RateGatewayService;

/**
 * Acceptance tests for UserController
 *
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CurrencyConverterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerAcceptanceTest {

    @MockBean
    private RateGatewayService rateGatewayService;

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

    @Test
    public void create() throws Exception {
        // given
        UserDto user = new UserDto();
        user.setUserName("test001");
        user.setPassword("pass001");

        // when
        UserDto resultUser = this.restTemplate.postForObject("/v1/users", user, UserDto.class);

        // then
        assertThat(resultUser.getUserName(), equalTo("test001"));
        assertThat(resultUser.getPassword(), equalTo("pass001"));
        assertThat(resultUser.getId(), notNullValue());
    }

    @Test
    public void create_exception() throws Exception {
        // given
        UserDto user = new UserDto();
        user.setUserName("test1");
        user.setPassword("test1");

        // when
        String body = this.restTemplate.postForObject("/v1/users", user, String.class);

        // then
        JSONObject json = new JSONObject(body);
        assertThat(json.get("status"), equalTo(400));
        assertThat(json.get("exception"), equalTo("com.spavlenko.exception.ConstraintsViolationException"));
    }

}
