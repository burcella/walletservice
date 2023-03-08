package com.cti.walletsystem.walletservice.repository;

import com.cti.walletsystem.walletservice.models.Transaction;
import com.cti.walletsystem.walletservice.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    Transaction findByCode(String code);
    Boolean deleteByCode(String code);


}
