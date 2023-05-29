package com.vpp97.demo.api.services;

import com.vpp97.demo.api.helpers.CurrencyExchangeHelper;
import com.vpp97.demo.api.repositories.ExchangeRateHistoryRepository;
import com.vpp97.demo.api.repositories.ExchangeRateLastRepository;
import com.vpp97.demo.dto.request.CreateCurrencyExchangeRequest;
import com.vpp97.demo.dto.request.CurrencyExchangeCalculationRequest;
import com.vpp97.demo.dto.request.UpdateCurrencyExchangeRequest;
import com.vpp97.demo.dto.response.CurrencyExchangeCalculationResponse;
import com.vpp97.demo.dto.response.CurrencyExchangeResponse;
import com.vpp97.demo.entities.Currency;
import com.vpp97.demo.entities.ExchangeRateLast;
import com.vpp97.demo.exceptions.ElementNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public CurrencyExchangeCalculationResponse calculateCurrencyExchange(CurrencyExchangeCalculationRequest currencyExchangeCalculationRequest) {
        Currency originCurrency = this.currencyExchangeHelper.getCurrencyByCode(currencyExchangeCalculationRequest.getOriginCurrencyCode());
        Currency targetCurrency = this.currencyExchangeHelper.getCurrencyByCode(currencyExchangeCalculationRequest.getTargetCurrencyCode());

        ExchangeRateLast originRate = this.exchangeRateLastRepository.findByCurrency_Id(originCurrency.getId()).orElseThrow(() -> new ElementNotFoundException("Exchange rate of origin currency not found"));
        ExchangeRateLast targetRate = this.exchangeRateLastRepository.findByCurrency_Id(targetCurrency.getId()).orElseThrow(() -> new ElementNotFoundException("Exchange rate of target currency not found"));


        BigDecimal calculationRate = originRate.getRate().divide(targetRate.getRate(), 4, RoundingMode.HALF_UP);
        BigDecimal targetAmount = currencyExchangeCalculationRequest.getOriginAmount().multiply(calculationRate);


        return CurrencyExchangeCalculationResponse.builder()
                .originCurrencyCode(originCurrency.getCode())
                .targetCurrencyCode(targetCurrency.getCode())
                .originAmount(currencyExchangeCalculationRequest.getOriginAmount())
                .targetAmount(targetAmount)
                .build();
    }

}
