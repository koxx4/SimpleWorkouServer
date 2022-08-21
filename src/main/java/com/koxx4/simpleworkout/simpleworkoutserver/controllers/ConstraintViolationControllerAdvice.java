package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
class ConstraintViolationControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Included request parameter has illegal structure")
    public void handleConstraintViolationException() {
        //pass
    }

}
