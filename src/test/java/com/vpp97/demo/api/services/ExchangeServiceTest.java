package com.vpp97.demo.api.services;

import com.vpp97.demo.api.helpers.CurrencyExchangeHelper;
import com.vpp97.demo.api.repositories.CurrencyRepository;
import com.vpp97.demo.api.repositories.ExchangeRateHistoryRepository;
import com.vpp97.demo.api.repositories.ExchangeRateLastRepository;
import com.vpp97.demo.dto.request.CurrencyExchangeCalculationRequest;
import com.vpp97.demo.dto.response.CurrencyExchangeCalculationResponse;
import com.vpp97.demo.dto.response.CurrencyExchangeResponse;
import com.vpp97.demo.entities.Currency;
import com.vpp97.demo.entities.ExchangeRateLast;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExchangeServiceTest {

    @Autowired
    ExchangeService exchangeService;
    @MockBean
    ExchangeRateHistoryRepository exchangeRateHistoryRepository;
    @MockBean
    ExchangeRateLastRepository exchangeRateLastRepository;
    @MockBean
    CurrencyRepository currencyRepository;

    @ParameterizedTest
    @CsvSource({
            "450.0,PMX,0.057,PEN,0.27,94.9950",
            "230.45,USD,1.0,PEN,0.27,853.5177",
            "1032.5698,EUR,1.07,PEN,0.27,4092.0741"
    })
    void calculateCurrencyExchange(BigDecimal amount,
                                   String originCode,
                                   BigDecimal originRate,
                                   String targetCode,
                                   BigDecimal targetRate,
                                   BigDecimal expectedAmount) {
        CurrencyExchangeCalculationRequest currencyExchangeCalculationRequest = CurrencyExchangeCalculationRequest.builder()
                .originAmount(amount)
                .originCurrencyCode(originCode)
                .targetCurrencyCode(targetCode)
                .build();

        when(this.currencyRepository
                .findByCode(originCode)).thenReturn(Optional.ofNullable(Currency.builder()
                        .id(1L)
                        .code(originCode)
                        .name("Coin 1")
                .build()));

        when(this.currencyRepository
                .findByCode(targetCode)).thenReturn(Optional.ofNullable(Currency.builder()
                .id(2L)
                .code(targetCode)
                .name("Coin 2")
                .build()));

        when(this.exchangeRateLastRepository
                .findByCurrency_Id(1L)).thenReturn(Optional.ofNullable(ExchangeRateLast.builder()
                        .id(1L)
                        .rate(originRate)
                .build()));

        when(this.exchangeRateLastRepository
                .findByCurrency_Id(2L)).thenReturn(Optional.ofNullable(ExchangeRateLast.builder()
                .id(2L)
                .rate(targetRate)
                .build()));

        CurrencyExchangeCalculationResponse currencyExchangeResponse = this.exchangeService.calculateCurrencyExchange(currencyExchangeCalculationRequest);

        assertEquals(expectedAmount,currencyExchangeResponse.getTargetAmount());
    }
}