package com.cti.walletsystem.walletservice.execption;

public class WalletNotFoundException extends RuntimeException{

    public WalletNotFoundException(Long walletId){
        super("no wallet found with id");
    }
    public WalletNotFoundException(String walletCode){
        super("no wallet found with code");
    }

}
