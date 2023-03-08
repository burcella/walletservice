package com.cti.walletsystem.walletservice.controller;

import com.cti.walletsystem.walletservice.dto.RefillDto;
import com.cti.walletsystem.walletservice.dto.TransactionResponse;
import com.cti.walletsystem.walletservice.dto.TransfertDto;
import com.cti.walletsystem.walletservice.models.Transaction;
import com.cti.walletsystem.walletservice.service.TransactionService;
import com.cti.walletsystem.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
   @Autowired
    TransactionService transactionService;
    @Autowired
    WalletService walletService;
    @PostMapping("/transfert")
    public ResponseEntity<TransactionResponse> tranfertByUserId(@RequestBody TransfertDto transaction) {

        return transactionService.createTransaction(transaction);
    }
    @PostMapping("/recharge")
    public ResponseEntity<TransactionResponse> rechargeWallet(@RequestBody RefillDto transaction){
        return transactionService.recharge(transaction);
    }
    @PostMapping("/retrait")
    public ResponseEntity<TransactionResponse> retraitWallet(@RequestBody RefillDto transaction) {
        return transactionService.retrait(transaction);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Transaction>>getAllTransactions(){
        return transactionService.getAllTransaction();
    }
    @GetMapping("/find/code/{code}")

    public ResponseEntity<TransactionResponse> getTransactionByCode1(@PathVariable String code){
        return transactionService.getTransactionByCode(code);
    }
    @GetMapping("/find/id/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById1(@PathVariable Long id){
        return transactionService.getTransactionById(id);
    }
//    public static Double createByAmount(TransactionRequest transactionRequest) {
//
//        Double amount = transactionRequest.getAmount();
//        return amount;
//    }
//
//
//
//
//    @PostMapping("/create")
//        @ResponseStatus(value = HttpStatus.CREATED)
//        public TransactionResponse createdtransaction(@RequestBody TransactionRequest  transaction){
//            return transactionService.createTransaction(transaction);
//
//    }
//    @GetMapping("/all")
//    @ResponseStatus(value = HttpStatus.OK)
//    public List<Transaction> gettransaction(){
//        System.out.println("getting all transactions");
//        return transactionService.getTransaction();
//    }
//    @GetMapping("/find/id/{id}")
//    public Optional<Transaction> gettransactionById(@PathVariable Long id){
//        System.out.println("getting transaction with id  %f"+id);
//        return transactionService.getTransactionById(id);
//
//    }
//    @GetMapping("/find/code/{transactionCode}")
//    public Transaction gettransactionByCode(@PathVariable String transactionCode){
//        System.out.println("getting transaction by code %s" + transactionCode);
//        return transactionService.getTransactionByCode(transactionCode);
//
//    }
//    @DeleteMapping("/delete/{id}")
//    public List<Wallet> deleteWalletByCode(@PathVariable Long id){
//        System.out.println("delete a wallet");
//        return walletService.deleteWalletById(id);
//        }
//        @PostMapping("/refil")
//    public TransactionResponse refillTransaction(@RequestBody RefillDto refillDto){
//    return transactionService.createTransaction(refillDto);
//    }
//    @PostMapping("/withdraw")
//    public TransactionResponse withdrawTransaction(@RequestBody RefillDto refillDto){
//       return transactionService.updateTransaction(refillDto);
//   }
//
//     PostMapping("/transfert")
//    public TransactionResponse transfertTransaction(@RequestBody TransfertDto transfertDto){
//        return transactionService.transfert(transfertDto);
//
//    }
//
}
