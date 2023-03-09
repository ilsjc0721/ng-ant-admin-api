package com.demo.app.service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Auth extends Authenticator {

    private String userName;
    private String password;

    public Auth(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        PasswordAuthentication pa = new PasswordAuthentication(userName, password);
        return pa;
    }
}
