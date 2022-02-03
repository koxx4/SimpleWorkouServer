package com.koxx4.simpleworkout.simpleworkoutserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Requested workout does not exist")
public class NoSuchWorkoutException extends Exception{
    public NoSuchWorkoutException() {
        super("Requested workout does not exist");
    }

    public NoSuchWorkoutException(String message) {
        super(message);
    }
}
