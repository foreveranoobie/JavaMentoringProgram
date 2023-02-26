package com.epam.storozhuk.exception;

public class EntityAbsentException extends RuntimeException {

    public EntityAbsentException() {
    }

    public EntityAbsentException(String message) {
        super(message);
    }
}
