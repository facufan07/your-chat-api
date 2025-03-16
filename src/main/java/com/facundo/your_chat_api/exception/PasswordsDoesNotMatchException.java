package com.facundo.your_chat_api.exception;

public class PasswordsDoesNotMatchException extends RuntimeException{
    public PasswordsDoesNotMatchException() {
    }

    public PasswordsDoesNotMatchException(String message) {
        super(message);
    }

    public PasswordsDoesNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
