package com.spavlenko.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.spavlenko.domain.Currency;
import com.spavlenko.dto.ExchangeRateDto;

/**
 * Exchange controller
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 *
 */
@RestController
@RequestMapping("v1/rates")
public class ExchangeRatesController {

    @PostMapping("/{userName}")
    @ResponseStatus(HttpStatus.CREATED)
    public ExchangeRateDto createRate(@Valid @PathVariable String userName, @Valid @RequestBody ExchangeRateDto rate) {
        // TODO to implement
        return null;
    }

    @GetMapping("/{userName}")
    public List<ExchangeRateDto> getRecentRates(@Valid @PathVariable String userName) {
        // TODO to implement
        return null;
    }

    @GetMapping("/{from}/{to}")
    public List<ExchangeRateDto> getRecentRates(@Valid @PathVariable Currency from, @Valid @PathVariable Currency to) {
        // TODO to implement
        return null;
    }
}
