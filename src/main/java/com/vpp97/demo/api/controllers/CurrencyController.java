package com.vpp97.demo.api.controllers;

import com.vpp97.demo.api.services.ExchangeService;
import com.vpp97.demo.dto.request.CreateCurrencyExchangeRequest;
import com.vpp97.demo.dto.request.UpdateCurrencyExchangeRequest;
import com.vpp97.demo.dto.response.CurrencyExchangeResponse;
import com.vpp97.demo.dto.response.FieldErrorsResponse;
import com.vpp97.demo.dto.response.SuccessfulControllerResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("currency")
@RestController
@RequiredArgsConstructor
@Tag(name = "Currency")
public class CurrencyController {
    private final ExchangeService exchangeService;

    @PostMapping(value = "rate", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Unable to create currency",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FieldErrorsResponse.class)) }) })
    public ResponseEntity<SuccessfulControllerResponse<CurrencyExchangeResponse>> createCurrencyExchange(@RequestBody @Valid CreateCurrencyExchangeRequest createCurrencyExchangeRequest){
        CurrencyExchangeResponse currencyExchangeResponse = this.exchangeService.createCurrencyExchange(createCurrencyExchangeRequest);
        SuccessfulControllerResponse genericControllerResponse = SuccessfulControllerResponse.<CurrencyExchangeResponse>builder()
                .message("Currency exchange created successfully")
                .data(currencyExchangeResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(genericControllerResponse);
    }

    @PostMapping(value = "{currencyId}/rate", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Unable to update currency",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FieldErrorsResponse.class)) }) })
    public ResponseEntity<SuccessfulControllerResponse<CurrencyExchangeResponse>> updateCurrencyExchange(@PathVariable("currencyId") Long currencyId, @RequestBody @Valid UpdateCurrencyExchangeRequest updateCurrencyExchangeRequest){
        CurrencyExchangeResponse currencyExchangeResponse = this.exchangeService.updateCurrencyExchange(currencyId, updateCurrencyExchangeRequest);
        SuccessfulControllerResponse genericControllerResponse = SuccessfulControllerResponse.<CurrencyExchangeResponse>builder()
                .message("Currency exchange rate updated successfully")
                .data(currencyExchangeResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(genericControllerResponse);
    }

}