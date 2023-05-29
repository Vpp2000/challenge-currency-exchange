package com.vpp97.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyExchangeCalculationRequest {
    @NotNull
    @NotEmpty
    private String originCurrencyCode;
    @NotNull
    @Positive
    private BigDecimal originAmount;
    @NotNull
    @NotEmpty
    private String targetCurrencyCode;
}
