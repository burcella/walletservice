package com.cti.walletsystem.walletservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransfertDto {
    Long userIdSender;
    BigDecimal amount;
    Long userIdReceive;

}
