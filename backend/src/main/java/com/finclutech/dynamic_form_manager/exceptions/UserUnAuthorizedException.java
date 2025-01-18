package com.finclutech.dynamic_form_manager.exceptions;

public class UserUnAuthorizedException extends RuntimeException{
    public UserUnAuthorizedException(String message) {
        super(message);
    }
}
