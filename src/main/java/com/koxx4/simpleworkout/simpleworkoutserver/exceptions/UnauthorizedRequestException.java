package com.koxx4.simpleworkout.simpleworkoutserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Unauthorized", code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedRequestException extends RuntimeException {

}
