package com.cti.walletsystem.walletservice.repository;

import com.cti.walletsystem.walletservice.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Wallet findByCode(String code);
    Boolean deleteByCode(String code);
    Wallet findByUserId(Long userId);


    // Wallet findByUser_id(Long user_id);
}

