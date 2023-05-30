package com.vpp97.demo.dto.response;


import com.vpp97.demo.entities.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyDetailResponse {
    private Long id;
    private String currencyName;
    private String currencyCode;
    private BigDecimal rate;

    public static CurrencyDetailResponse createFromCurrencyEntity(Currency currency){
        return CurrencyDetailResponse.builder()
                .id(currency.getId())
                .currencyCode(currency.getCode())
                .currencyName(currency.getName())
                .rate(currency.getExchangeRateLast().getRate())
                .build();
    }
}
