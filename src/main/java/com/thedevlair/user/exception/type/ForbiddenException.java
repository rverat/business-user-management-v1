package com.thedevlair.user.exception.type;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}