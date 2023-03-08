package com.cti.walletsystem.walletservice.execption;

public class WalletBadRequestException extends RuntimeException{
    public WalletBadRequestException(String message){
        super("error request" +message);
    }
}
