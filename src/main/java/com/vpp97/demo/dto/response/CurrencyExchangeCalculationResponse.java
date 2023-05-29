package com.vpp97.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyExchangeCalculationResponse {
    private String originCurrencyCode;
    private BigDecimal originAmount;
    private String targetCurrencyCode;
    private BigDecimal targetAmount;
}
