package com.vpp97.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCurrencyExchangeRequest {
    @NotNull
    @NotEmpty
    private String currencyName;
    @NotNull
    @NotEmpty
    private String currencyCode;
    @NotNull
    @Positive
    private BigDecimal rate;
}
