package com.cti.walletsystem.walletservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletDto {
    private String code = "";
    private Boolean isActive=false;
    private BigDecimal balance= BigDecimal.valueOf(0);
    private Long userId=null;
}

