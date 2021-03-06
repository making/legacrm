package com.example.poc.legacrm.customer;

import java.time.Instant;

public class AuthLoginBuilder {

    private String lastPassword;

    private Instant loginDateTime;

    private Boolean loginFlg;

    private String membershipNumber;

    private String password;

    public AuthLogin createAuthLogin() {
        return new AuthLogin(lastPassword, loginDateTime, loginFlg, membershipNumber, password);
    }

    public AuthLoginBuilder setLastPassword(String lastPassword) {
        this.lastPassword = lastPassword;
        return this;
    }

    public AuthLoginBuilder setLoginDateTime(Instant loginDateTime) {
        this.loginDateTime = loginDateTime;
        return this;
    }

    public AuthLoginBuilder setLoginFlg(Boolean loginFlg) {
        this.loginFlg = loginFlg;
        return this;
    }

    public AuthLoginBuilder setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
        return this;
    }

    public AuthLoginBuilder setPassword(String password) {
        this.password = password;
        return this;
    }
}