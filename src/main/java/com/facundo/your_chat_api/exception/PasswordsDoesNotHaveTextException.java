package com.facundo.your_chat_api.exception;

public class PasswordsDoesNotHaveTextException extends RuntimeException{
    public PasswordsDoesNotHaveTextException() {
    }

    public PasswordsDoesNotHaveTextException(String message) {
        super(message);
    }

    public PasswordsDoesNotHaveTextException(String message, Throwable cause) {
        super(message, cause);
    }
}
