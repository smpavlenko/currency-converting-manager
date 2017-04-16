package com.spavlenko.controller.request;

/**
 * User request
 * 
 * @author sergii.pavlenko
 * @since Apr 16, 2017
 */
public class UserRequest {
    private String userName;
    private String password;
    private String passwordConfirm;

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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

}
