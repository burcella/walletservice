package com.cti.walletsystem.walletservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private String code;
    private String reference;
    private Date create;
    private String state;
    private BigDecimal amount;
    private Long senderId;
    private Long receiveId;

}
