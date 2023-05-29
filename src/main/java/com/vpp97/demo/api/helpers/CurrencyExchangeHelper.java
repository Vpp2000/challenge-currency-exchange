package com.vpp97.demo.api.helpers;

import com.vpp97.demo.api.repositories.CurrencyRepository;
import com.vpp97.demo.api.repositories.ExchangeRateHistoryRepository;
import com.vpp97.demo.api.repositories.ExchangeRateLastRepository;
import com.vpp97.demo.dto.request.CreateCurrencyExchangeRequest;
import com.vpp97.demo.dto.request.UpdateCurrencyExchangeRequest;
import com.vpp97.demo.dto.response.CurrencyExchangeResponse;
import com.vpp97.demo.entities.Currency;
import com.vpp97.demo.entities.ExchangeRateHistory;
import com.vpp97.demo.entities.ExchangeRateLast;
import com.vpp97.demo.exceptions.ElementNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeHelper {
    private final CurrencyRepository currencyRepository;
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;
    private final ExchangeRateLastRepository exchangeRateLastRepository;

    @Transactional
    public CurrencyExchangeResponse createCurrencyExchange(final CreateCurrencyExchangeRequest createCurrencyExchangeRequest){
        Currency currency = Currency.builder()
                .name(createCurrencyExchangeRequest.getCurrencyName())
                .code(createCurrencyExchangeRequest.getCurrencyCode())
                .build();
        this.currencyRepository.save(currency);


        ExchangeRateLast exchangeRateLast = ExchangeRateLast.builder()
                .currency(currency)
                .rate(createCurrencyExchangeRequest.getRate())
                .build();
        this.exchangeRateLastRepository.save(exchangeRateLast);

        ExchangeRateHistory exchangeRateHistory = ExchangeRateHistory.builder()
                .currencyName(currency.getName())
                .currencyCode(currency.getCode())
                .rate(exchangeRateLast.getRate())
                .build();
        this.exchangeRateHistoryRepository.save(exchangeRateHistory);


        return CurrencyExchangeResponse.builder()
                .createdAt(exchangeRateHistory.getCreatedAt())
                .currencyName(currency.getName())
                .rate(exchangeRateLast.getRate())
                .build();
    }

    @Transactional
    public CurrencyExchangeResponse updateCurrencyExchange(Long currencyId, UpdateCurrencyExchangeRequest updateCurrencyExchangeRequest){
        Currency currency = this.currencyRepository.findById(currencyId).orElseThrow(() -> new ElementNotFoundException("Currency not found"));

        ExchangeRateLast exchangeRateLast = this.exchangeRateLastRepository.findByCurrency_Id(currency.getId()).orElseThrow(() -> new ElementNotFoundException("Exchange rate of currency not found"));
        exchangeRateLast.setRate(updateCurrencyExchangeRequest.getRate());
        this.exchangeRateLastRepository.save(exchangeRateLast);


        ExchangeRateHistory exchangeRateHistory = ExchangeRateHistory.builder()
                .currencyName(currency.getName())
                .currencyCode(currency.getCode())
                .rate(exchangeRateLast.getRate())
                .build();

        this.exchangeRateHistoryRepository.save(exchangeRateHistory);

        return CurrencyExchangeResponse.builder()
                .createdAt(exchangeRateHistory.getCreatedAt())
                .currencyName(exchangeRateHistory.getCurrencyName())
                .rate(exchangeRateHistory.getRate())
                .build();
    }

    public Currency getCurrencyByCode(String currencyCode) {
        Currency currency = this.currencyRepository.findByCode(currencyCode).orElseThrow(() -> new ElementNotFoundException("Currency not found"));
        return currency;
    }
}
