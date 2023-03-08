package com.cti.walletsystem.walletservice.execption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionExceptionHandler {
    @ExceptionHandler(value = TransactionNotFoundException.class)
    public ResponseEntity<Object> notFoundException(TransactionNotFoundException exception){
        return new ResponseEntity<>( "transactiont not found", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = TransactionBadRequestException.class)
    public ResponseEntity<Object> badRequestException(TransactionBadRequestException exception){
        return new ResponseEntity<>( "bad Request to create transaction:check if user is not having a wallet", HttpStatus.BAD_REQUEST);
    }
}
