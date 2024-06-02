package com.alba.uc.controller;

import com.alba.uc.controller.dto.MessageError;
import com.alba.uc.exception.ResourceAlreadyExistsException;
import com.alba.uc.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public MessageError handleResourceAlreadyExistsException(
            UnauthorizedException unauthorized) {

        return new MessageError(unauthorized.getMessage());

    }

    @ExceptionHandler({ResourceAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageError handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException resourceAlreadyExistsException) {

        return new MessageError(resourceAlreadyExistsException.getMessage());

    }

}