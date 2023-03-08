package com.cti.walletsystem.walletservice.controller;

import com.cti.walletsystem.walletservice.dto.TransactionRequest;
import com.cti.walletsystem.walletservice.dto.TransactionResponse;
import com.cti.walletsystem.walletservice.dto.WalletDto;
import com.cti.walletsystem.walletservice.dto.WalletResponse;
import com.cti.walletsystem.walletservice.models.Transaction;
import com.cti.walletsystem.walletservice.models.Wallet;
import com.cti.walletsystem.walletservice.repository.WalletRepository;
import com.cti.walletsystem.walletservice.service.TransactionService;
import com.cti.walletsystem.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    WalletService walletService;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionController transactionController;
    @Autowired
    private TransactionService transactionService;


    @PostMapping("/create")
    public ResponseEntity<WalletResponse> createdWallets(@RequestBody WalletDto wallet){
        return walletService.createdWallet(wallet);
    }
    @PostMapping("/activate/{id}")
    public ResponseEntity<WalletResponse> activateWallet(@PathVariable Long id){
        return walletService.activated(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Wallet> >getAllWallett(){
        System.out.println("getting all wallets");
        return walletService.getAllWallet();
    }
    @GetMapping("/find/id/{id}")
    public ResponseEntity<WalletResponse> getWalletById(@PathVariable Long id){
        System.out.println("getting wallet with id  %f"+id);
        return walletService.getWaletById(id);

    }
    @GetMapping("/find/code/{walletCode}")
    public ResponseEntity<WalletResponse> getWalletByCode(@PathVariable String walletCode){
        System.out.println("getting wallet by code %s" + walletCode);
        return walletService.getwalletByCode(walletCode);

    }
  //  }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<Wallet>> deleteWalletByCode(@PathVariable Long id){
        System.out.println("delete a wallet");
        return walletService.deleteWalletById(id);

    }
    @PutMapping("/update")
    public  ResponseEntity<WalletResponse> updateWallet(@RequestBody WalletDto wallet){
       return walletService.updateWallet(wallet);
    }


    /*public void refilWallet(Double amount){

        Double amount1 = TransactionController.createByAmount(transactionRequest);
        if(transactionService. != walletService.createWallet(wallet).getCode())

    }
*/
     @GetMapping("/find/user/{userId}")
    public Wallet getWalletFromUserId(@PathVariable Long userId){
         return  walletService.getWalletByUserId(userId) ;
     }

}
