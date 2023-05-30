package com.vpp97.demo.api.services;

import com.vpp97.demo.api.repositories.CurrencyRepository;
import com.vpp97.demo.dto.response.CurrencyDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public List<CurrencyDetailResponse> getAllCurrencyDetails(){
        return this.currencyRepository.findAll()
                .stream()
                .map(CurrencyDetailResponse::createFromCurrencyEntity)
                .collect(Collectors.toList());
    }
}
