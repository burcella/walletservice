package com.cti.walletsystem.walletservice.execption;

public class TransactionBadRequestException extends RuntimeException{
    public  TransactionBadRequestException(String message){
        super("error request" +message);

    }
}
