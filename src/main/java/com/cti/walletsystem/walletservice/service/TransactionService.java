package com.cti.walletsystem.walletservice.service;

import com.cti.walletsystem.walletservice.dto.*;
import com.cti.walletsystem.walletservice.execption.TransactionBadRequestException;
import com.cti.walletsystem.walletservice.execption.TransactionInsuffissantException;
import com.cti.walletsystem.walletservice.execption.WalletNotFoundException;
import com.cti.walletsystem.walletservice.models.Transaction;
import com.cti.walletsystem.walletservice.models.Wallet;
import com.cti.walletsystem.walletservice.repository.TransactionRepository;
import com.cti.walletsystem.walletservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class    TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    WalletService walletService;
    @Autowired
    WalletRepository walletRepository;


    public ResponseEntity<TransactionResponse> createTransaction(TransfertDto transaction) {


        // On recupere les wallet
        Wallet walletSender = walletRepository.findByUserId(transaction.getUserIdSender());
        Wallet walletReceive = walletRepository.findByUserId(transaction.getUserIdReceive());
        System.out.println("walletReceive = " + walletReceive);


        //les wallets sont null ou pas
        if (walletSender != null && walletReceive != null) {
            // on recupere les balances des deux
            BigDecimal balanceSender = walletSender.getBalance();
            BigDecimal balanceReceive = walletReceive.getBalance();
            System.out.println("walletReceive.getBalance() = " + walletReceive.getBalance());


            // on fait le retrait
            Wallet walletSender1 = walletService.withdraw(transaction.getUserIdSender(), transaction.getAmount());
            BigDecimal balanceSender1 = walletSender1.getBalance();
            System.out.println("walletSender1.getBalance() = " + walletSender1.getBalance());

            // on recurepe le depot
            Wallet walletReceive1 = walletService.refil(walletReceive.getUserId(), transaction.getAmount());
            BigDecimal balanceReceive1 = walletReceive1.getBalance();


            String shortCode = UUID.randomUUID().toString();

            if ((balanceSender.compareTo(balanceSender1) >0) && (balanceReceive.compareTo(balanceReceive1) < 0)){
                walletRepository.save(walletReceive1);
                walletRepository.save(walletSender1);
                Transaction transactionToSaveSender = Transaction.builder()
                        .code("cti" + shortCode)
                        .reference("transaction" + shortCode)
                        .create(new Date())
                        .state("true")
                        .amount(transaction.getAmount())
                        .senderId(transaction.getUserIdSender())
                        .receiveId(transaction.getUserIdReceive())
                        .build();
                System.out.println("transaction" +transactionToSaveSender);
                Transaction transactionSender = transactionRepository.save(transactionToSaveSender);
                Transaction transactionToSaveReceive = Transaction.builder()
                        .code("cti" + shortCode)
                        .reference("transaction" + shortCode)
                        .create(new Date())
                        .state("true")
                        .amount(balanceSender1)
                        .senderId(transaction.getUserIdReceive())
                        .receiveId(transaction.getUserIdReceive())
                        .build();
                Transaction transactionReceive = transactionRepository.save(transactionToSaveReceive);

                if ((transactionSender.getId() != null) &&(transactionReceive.getId() != null)){
                    TransactionResponse transactionResponse1 = TransactionResponse.builder()
                            .id(transactionSender.getId())
                            .reference(transactionSender.getReference())
                            .code(transactionSender.getCode())
                            .amount(transactionSender.getAmount())
                            .state(transactionSender.getState())
                            .create(transactionSender.getCreate())
                            .senderId(transactionSender.getSenderId())
                            .receiveId(transactionReceive.getReceiveId())
                            .build();

                    return new ResponseEntity<>(transactionResponse1, HttpStatus.CREATED);
                }
            }


        }

        throw new TransactionBadRequestException("this transaction was not possible the wallet is null");
    }

    public ResponseEntity<TransactionResponse> recharge(RefillDto transaction) {
        Wallet wallet = walletRepository.findByUserId(transaction.getUserId());
        BigDecimal balance = BigDecimal.valueOf(0);

        if (wallet != null) {
            BigDecimal balance1 = wallet.getBalance();
            Wallet wallet1 = walletService.refil(transaction.getUserId(), transaction.getBalance());
            balance = wallet1.getBalance();
            wallet.setBalance(balance);
            Transaction transaction1 = null;
            if (balance1.compareTo(balance) < 0) {
                walletRepository.save(wallet1);
                String shortCode = UUID.randomUUID().toString();
                Transaction transactionToSave = Transaction.builder()
                        .code("cti" + shortCode)
                        .reference("transaction" + shortCode)
                        .create(new Date())
                        .state("true")
                        .amount(transaction.getBalance())
                        .senderId(transaction.getUserId())
                        .receiveId(transaction.getUserId())
                        .build();
                transaction1 = transactionRepository.save(transactionToSave);
            }
            if (transaction1.getId() != null) {
                TransactionResponse transactionResponse = TransactionResponse.builder()
                        .id(transaction1.getId())
                        .reference(transaction1.getReference())
                        .code(transaction1.getCode())
                        .amount(transaction1.getAmount())
                        .state(transaction1.getState())
                        .create(transaction1.getCreate())
                        .senderId(transaction1.getSenderId())
                        .receiveId(transaction1.getReceiveId())
                        .build();
                return new ResponseEntity<>(transactionResponse, HttpStatus.CREATED);
            } else {
                throw new TransactionBadRequestException("error to save the user wallet");
            }
        } else {
            throw new TransactionBadRequestException("this transaction was");
        }


    }

    public ResponseEntity<TransactionResponse> retrait(RefillDto transaction) {
        Wallet wallet = walletRepository.findByUserId(transaction.getUserId());
        BigDecimal balance = BigDecimal.valueOf(0);
        BigDecimal balance1 = wallet.getBalance();
        BigDecimal balance2 = transaction.getBalance();
        if (wallet != null) {
            if (balance1.compareTo(balance2) > 0) {

                Wallet wallet1 = walletService.withdraw(transaction.getUserId(), transaction.getBalance());
                balance = wallet1.getBalance();
                wallet.setBalance(balance);
                Transaction transaction1 = null;
                if (balance1.compareTo(balance) > 0) {
                    walletRepository.save(wallet1);
                    String shortCode = UUID.randomUUID().toString();
                    Transaction transactionToSave = Transaction.builder()
                            .code("cti" + shortCode)
                            .reference("transaction" + shortCode)
                            .create(new Date())
                            .state("true")
                            .amount(transaction.getBalance())
                            .senderId(transaction.getUserId())
                            .receiveId(transaction.getUserId())
                            .build();
                    transaction1 = transactionRepository.save(transactionToSave);
                }
                if (transaction1.getId() != null) {
                    TransactionResponse transactionResponse = TransactionResponse.builder()
                            .id(transaction1.getId())
                            .reference(transaction1.getReference())
                            .code(transaction1.getCode())
                            .amount(transaction1.getAmount())
                            .state(transaction1.getState())
                            .create(transaction1.getCreate())
                            .senderId(transaction1.getSenderId())
                            .receiveId(transaction1.getReceiveId())
                            .build();
                    return new ResponseEntity<>(transactionResponse, HttpStatus.CREATED);
                } else {
                    throw new TransactionBadRequestException("error to save the user wallet");
                }
            } else {
                throw new TransactionInsuffissantException("amount is insufisant");
            }

        }

        throw new TransactionBadRequestException("error to save the user wallet");

    }
    public ResponseEntity<List<Transaction>> getAllTransaction(){
        List<Transaction> transactions = transactionRepository.findAll().stream().toList();

        return new ResponseEntity<>(transactions,HttpStatus.OK);
    }
    public ResponseEntity<TransactionResponse> getTransactionByCode(String code) {

        Transaction transaction = transactionRepository.findByCode(code);

        if (transaction != null){
            if(transaction.getCode()!=null){
                TransactionResponse transactionResponse = TransactionResponse.builder()
                        .id(transaction.getId())
                        .code(transaction.getCode())
                        .reference(transaction.getReference())
                        .receiveId(transaction.getReceiveId())
                        .senderId(transaction.getSenderId())
                        .state(transaction.getState())
                        .create(transaction.getCreate())
                        .amount(transaction.getAmount())
                        .build();
                return new ResponseEntity<>(transactionResponse,HttpStatus.OK);

            }else{
                throw new WalletNotFoundException(code);
            }

        }else{
            throw new WalletNotFoundException(code);
        }





    }
    public ResponseEntity<TransactionResponse> getTransactionById(Long id){
    Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new WalletNotFoundException(id));
        if(transaction.getId() != null){
            TransactionResponse transactionResponse = TransactionResponse.builder()
                    .id(transaction.getId())
                    .code(transaction.getCode())
                    .reference(transaction.getReference())
                    .receiveId(transaction.getReceiveId())
                    .senderId(transaction.getSenderId())
                    .state(transaction.getState())
                    .create(transaction.getCreate())
                    .amount(transaction.getAmount())
                    .build();
            return new ResponseEntity<>(transactionResponse,HttpStatus.OK);

        }else{
            throw new WalletNotFoundException(id);
        }
    }
//    public Wallet deleteTransactionById(Long id, Long userId){
//        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));
//        Wallet wallet = walletRepository.findByUserId(userId);
//        BigDecimal balance= transaction.getAmount();
//
//        if(id != null){
//            transactionRepository.deleteById(id);
//            Wallet wallet1= walletService.withdraw(userId,balance);
//            Wallet wallet2=walletService.updateWallet(wallet1);
//            return walletRepository.save(wallet2);
//
//
//
//
//        }
//        return wallet;
//    }
}









