package com.maveric.crm.exceptions;

public class CustomerDetailsNotFoundException extends Exception{
    public CustomerDetailsNotFoundException() {
        super();
    }

    public CustomerDetailsNotFoundException(String message) {
        super(message);
    }

    public CustomerDetailsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerDetailsNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CustomerDetailsNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
