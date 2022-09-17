package com.koxx4.simpleworkout.simpleworkoutserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Invalid old password", code = HttpStatus.BAD_REQUEST)
public class InvalidOldPasswordProvidedException extends Exception {
    public InvalidOldPasswordProvidedException() {
        super("Invalid old password provided");
    }

    public InvalidOldPasswordProvidedException(String message) {
        super(message);
    }
}
