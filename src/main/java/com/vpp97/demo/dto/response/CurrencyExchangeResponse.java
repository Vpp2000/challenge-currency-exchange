package com.vpp97.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExchangeResponse {
    private String currencyName;
    private BigDecimal rate;
    private LocalDateTime createdAt;
}
