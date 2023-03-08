package com.cti.walletsystem.walletservice.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="tb_transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(name = "transaction_code")
    private String code;
    @NonNull
    @Column(name = "transaction_reference")
    private String reference;
    @NonNull
    @Column(name = "transaction_create")
    private Date create;
    @NonNull
    @Column(name = "transaction_state")
    private String state;
    @NonNull
    @Column(name = "transaction_amount")
    private BigDecimal amount;

    @Column(name = "transaction_senderID" , nullable = true )
    private Long senderId;
    @Column(name = "transaction_receiveID" , nullable = true)
    private Long receiveId;


}
