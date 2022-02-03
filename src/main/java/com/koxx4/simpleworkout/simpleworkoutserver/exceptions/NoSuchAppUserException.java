package com.koxx4.simpleworkout.simpleworkoutserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Requested user does not exist")
public class NoSuchAppUserException extends Exception {
    public NoSuchAppUserException() {
        super("Requested app user does not exist.");
    }

    public NoSuchAppUserException(String message) {
        super(message);
    }
}
