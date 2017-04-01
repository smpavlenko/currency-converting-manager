package com.spavlenko.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User data transfer object
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @JsonIgnore
    private Long id;
    @NotNull(message = "User Name can not be null!")
    private String userName;
    @NotNull(message = "Password can not be null!")
    private String password;

    @JsonProperty
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
