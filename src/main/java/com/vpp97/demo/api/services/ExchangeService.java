package com.vpp97.demo.api.services;

import com.vpp97.demo.api.helpers.CurrencyExchangeHelper;
import com.vpp97.demo.api.repositories.ExchangeRateHistoryRepository;
import com.vpp97.demo.api.repositories.ExchangeRateLastRepository;
import com.vpp97.demo.dto.request.CreateCurrencyExchangeRequest;
import com.vpp97.demo.dto.request.UpdateCurrencyExchangeRequest;
import com.vpp97.demo.dto.response.CurrencyExchangeResponse;
import com.vpp97.demo.entities.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final ExchangeRateLastRepository exchangeRateLastRepository;
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;
    private final CurrencyExchangeHelper currencyExchangeHelper;

    @Transactional
    public CurrencyExchangeResponse createCurrencyExchange(CreateCurrencyExchangeRequest createCurrencyExchangeRequest){
        CurrencyExchangeResponse currencyExchangeResponse = this.currencyExchangeHelper.createCurrencyExchange(createCurrencyExchangeRequest);
        return currencyExchangeResponse;
    }

    @Transactional
    public CurrencyExchangeResponse updateCurrencyExchange(Long currencyId, UpdateCurrencyExchangeRequest updateCurrencyExchangeRequest){
        CurrencyExchangeResponse currencyExchangeResponse = this.currencyExchangeHelper.updateCurrencyExchange(currencyId, updateCurrencyExchangeRequest);
        return currencyExchangeResponse;
    }

}
