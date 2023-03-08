package com.cti.walletsystem.walletservice.execption;

public class TransactionInsuffissantException extends RuntimeException{
    public TransactionInsuffissantException(String message){
        super("error request" +message);
    }
}
