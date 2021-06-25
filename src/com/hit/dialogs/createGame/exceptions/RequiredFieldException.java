package com.hit.dialogs.createGame.exceptions;

public class RequiredFieldException extends Exception{
    public RequiredFieldException() {
    }

    public RequiredFieldException(String message) {
        super(message);
    }

    public RequiredFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequiredFieldException(Throwable cause) {
        super(cause);
    }

    public RequiredFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
