package com.cti.walletsystem.walletservice.execption;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(Long transactionId){
        super("no transaction found with id");
    }
}
