package com.cti.walletsystem.walletservice.execption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // IL RECUPERE LES EXCEPTIONS
public class WalletExectionHandler {
    @ExceptionHandler(value = WalletNotFoundException.class)
    public ResponseEntity<Object> notFoundException(WalletNotFoundException exception){
        return new ResponseEntity<>( "Wallet not found", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = WalletBadRequestException.class)
    public ResponseEntity<Object> badRequestException(WalletBadRequestException exception){
        return new ResponseEntity<>( "bad Request to create Wallet:check if user is not having a wallet", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = TransactionInsuffissantException.class)
    public ResponseEntity<Object> insuffissantException(TransactionInsuffissantException exception){
        return new ResponseEntity<>( "amount is insuffisant", HttpStatus.BAD_REQUEST);
    }

}


