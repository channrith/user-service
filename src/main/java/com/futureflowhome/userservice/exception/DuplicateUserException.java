package com.futureflowhome.userservice.exception;

public class DuplicateUserException extends RuntimeException {

    private final String field; // "username" or "email"

    public DuplicateUserException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
