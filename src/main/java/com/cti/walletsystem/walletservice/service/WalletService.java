package com.cti.walletsystem.walletservice.service;

import com.cti.walletsystem.walletservice.dto.WalletDto;
import com.cti.walletsystem.walletservice.dto.WalletResponse;
import com.cti.walletsystem.walletservice.execption.TransactionInsuffissantException;
import com.cti.walletsystem.walletservice.execption.WalletBadRequestException;
import com.cti.walletsystem.walletservice.execption.WalletNotFoundException;
import com.cti.walletsystem.walletservice.models.Wallet;
import com.cti.walletsystem.walletservice.repository.WalletRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class WalletService {
    @Autowired
    WalletRepository walletRepository;

    Logger logger;

    public WalletResponse createWallet(WalletDto wallet) {
        WalletDto wallet1 = WalletDto.builder()
                .code(wallet.getCode())
                .isActive(wallet.getIsActive())
                .balance(wallet.getBalance())
                .userId(wallet.getUserId())
                .build();
        Wallet walletToSave = mapWalletRequestToWallet(wallet1);
        Wallet wallet2 = walletRepository.save(walletToSave);
        System.out.println("Id Saved in database{}  %f" + wallet2.getId());
        WalletResponse walletResponse = WalletResponse.builder()
                .id(wallet2.getId())
                .code(wallet2.getCode())
                .isActive(wallet2.getIsActive())
                .balance(wallet2.getBalance())
                .userId(wallet2.getUserId())

                .build();
        return walletResponse;
    }

    public Wallet mapWalletRequestToWallet(WalletDto walletDto) {
        Wallet wallet = Wallet.builder()
                .code(walletDto.getCode())
                .isActive(walletDto.getIsActive())
                .balance(walletDto.getBalance())
                .userId(walletDto.getUserId())
                .build();
        return wallet;
    }

    public Optional<Wallet> getWalletById(Long id) {
        return Optional.of(walletRepository.findById(id).get());
    }

    public List<Wallet> getWallets() {
        List<Wallet> wallets = walletRepository.findAll().stream().toList();
        return wallets;
    }
    public ResponseEntity<List<Wallet>> getAllWallet(){
        List<Wallet> wallet = walletRepository.findAll().stream().toList();

        return new ResponseEntity<>(wallet,HttpStatus.OK);
    }

    public ResponseEntity<WalletResponse> getwalletByCode(String code) {

        Wallet wallet = walletRepository.findByCode(code);

        if (wallet != null){
            if(wallet.getCode()!=null){
                WalletResponse walletResponse = WalletResponse.builder()
                        .userId(wallet.getUserId())
                        .id(wallet.getId())
                        .balance(wallet.getBalance())
                        .isActive(wallet.getIsActive())
                        .code(wallet.getCode())
                        .message("success")
                        .build();
                return new ResponseEntity<>(walletResponse,HttpStatus.OK);

            }else{
                throw new WalletNotFoundException(code);
            }

        }else{
            throw new WalletNotFoundException(code);
        }





        }


    public ResponseEntity<WalletResponse> updateWallet(WalletDto wallet) {
        Optional<ResponseEntity<WalletResponse>> wallet1 = Optional.ofNullable(getwalletByCode(wallet.getCode()));

        if (wallet1 != null) {

            Wallet walletToSave = Wallet.builder()
                    .code(wallet.getCode())
                    .isActive(wallet.getIsActive())
                    .balance(wallet.getBalance())
                    .userId(wallet.getUserId())
                    .build();
            Wallet wallets = walletRepository.save(walletToSave);
            if (wallets.getId() != null) {
                WalletResponse walletResponse1 = WalletResponse.builder()
                        .id(wallets.getId())
                        .userId(wallets.getUserId())
                        .code(wallets.getCode())
                        .balance(wallets.getBalance())
                        .isActive(wallets.getIsActive())
                        .message("sucess")
                        .build();
                return new ResponseEntity<>(walletResponse1, HttpStatus.CREATED);
            } else
                throw new WalletBadRequestException("error to save the user wallet");
        }
        throw new WalletBadRequestException("this user already has a wallet");

    }

    public ResponseEntity<List<Wallet>> deleteWalletById(Long id) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));
        if (wallet.getId() != null) {
            walletRepository.deleteById(id);
            ResponseEntity<List<Wallet>> wallets =  getAllWallet();
            return wallets;

        } else {
            throw new WalletNotFoundException(id);
        }
    }

    public Wallet getWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId);


    }

    public Wallet refil(Long userId, BigDecimal amount) {

        BigDecimal balance = BigDecimal.valueOf(0);
        Wallet wallet = getWalletByUserId(userId);
        if (wallet != null){
            if (wallet.getId() != null && wallet.getIsActive() == false) {
                balance = wallet.getBalance().add(amount);
                wallet.setBalance(balance);
                walletRepository.save(wallet);
                return wallet;
            }else{
                throw new WalletNotFoundException(userId);
            }

        }
        throw new WalletNotFoundException(userId);
    }











    public Wallet withdraw(Long userId, BigDecimal amount) {
        BigDecimal balance = BigDecimal.valueOf(0);
        Wallet wallet = getWalletByUserId(userId);
        BigDecimal balanceCompte = wallet.getBalance();
        System.out.println("wallet = "+wallet);
        if( balanceCompte.compareTo(amount)>0){
            if (wallet != null){
                if (wallet.getId() != null && wallet.getIsActive() == false) {
                   balance = wallet.getBalance().subtract(amount);
                   wallet.setBalance(balance);
                   walletRepository.save(wallet);
                   return wallet;
                } else {
                    throw new WalletNotFoundException(userId);
                }

            }else {
                throw new WalletNotFoundException(userId);

            }
        }
        throw new TransactionInsuffissantException("amount is insuffisant");
    }
    public ResponseEntity<WalletResponse> createdWallet(WalletDto walletDto) {
        //WalletResponse walletResponse=WalletResponse.builder().build();
        if (walletDto.getUserId() != null) {
            Wallet wallet = walletRepository.findByUserId(walletDto.getUserId());
            if (wallet == null) {

                String shortCode = UUID.randomUUID().toString();
                System.out.println("generated code" + shortCode);
                Wallet walletToSave = Wallet.builder()
                        .code("cti" + shortCode)
                        .balance(BigDecimal.valueOf(0))
                        .userId(walletDto.getUserId())
                        .isActive(false)
                        .build();

                Wallet wallet1 = walletRepository.save(walletToSave);
                if (wallet1.getId() != null) {
                    WalletResponse walletResponse1 = WalletResponse.builder()
                            .id(wallet1.getId())
                            .userId(wallet1.getUserId())
                            .code(wallet1.getCode())
                            .balance(wallet1.getBalance())
                            .isActive(wallet1.getIsActive())
                            .message("sucess")
                            .build();
                    return new ResponseEntity<>(walletResponse1, HttpStatus.CREATED);
                } else
                    throw new WalletBadRequestException("error to save the user wallet");
            }
        }

            throw new WalletBadRequestException("this user already has a wallet");


    }

    public ResponseEntity<WalletResponse> activated(Long id) {
        Wallet wallet= walletRepository.findById(id).orElseThrow(()-> new WalletNotFoundException(id));
        // si elle ne retrouve pas le wallet avec cette id il leve exception
        if(wallet != null){
            if(wallet.getIsActive()== false){
                wallet.setIsActive(true);
            }
            Wallet saveWallet = walletRepository.save(wallet);
            WalletResponse walletResponse=WalletResponse.builder()
                    .message("wallet activated successfuly")
                    .code(saveWallet.getCode())
                    .isActive(saveWallet.getIsActive())
                    .balance(saveWallet.getBalance())
                    .id(saveWallet.getId())
                    .userId(saveWallet.getUserId())
                    .build();
            return new ResponseEntity<>(walletResponse,HttpStatus.OK);
        }else{
            throw new WalletNotFoundException(id);
        }
    }
    public ResponseEntity<WalletResponse> getWaletById(Long id){
        Wallet wallet = walletRepository.findById(id).orElseThrow(()-> new WalletNotFoundException(id));
        if(wallet.getId() != null){
          WalletResponse walletResponse = WalletResponse.builder()
                  .userId(wallet.getUserId())
                  .id(wallet.getId())
                  .balance(wallet.getBalance())
                  .isActive(wallet.getIsActive())
                  .code(wallet.getCode())
                  .message("sucess")
                  .build();
            return new ResponseEntity<>(walletResponse,HttpStatus.OK);

        }else{
            throw new WalletNotFoundException(id);
        }
    }
}









