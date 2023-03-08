package com.cti.walletsystem.walletservice.models;



import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_wallets")
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "wallet_code")
    private String code;

    @NonNull
    @Column(name = "wallet_isActive")
    private Boolean isActive;



    @NonNull
    @Column(name = "wallet_balance")
    private BigDecimal balance;

    @NonNull
    @Column(name = "wallet_user_id")
    private Long userId;


}
