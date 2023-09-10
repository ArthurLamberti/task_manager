package com.taskmanager.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ValidationException extends RuntimeException{

    private String message;

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

}
