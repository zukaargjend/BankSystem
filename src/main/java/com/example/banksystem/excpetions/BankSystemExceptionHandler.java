package com.example.banksystem.excpetions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class BankSystemExceptionHandler {

    @ExceptionHandler(value = {BankSystemRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(BankSystemRequestException exc){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        BankSystemException apiException = new BankSystemException(
                exc.getMessage(),
                exc,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequest);
    }
}
