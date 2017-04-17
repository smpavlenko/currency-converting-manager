package com.spavlenko.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spavlenko.controller.mapper.ExchangeRateMapper;
import com.spavlenko.domain.Currency;
import com.spavlenko.domain.ExchangeRate;
import com.spavlenko.domain.User;
import com.spavlenko.dto.ExchangeRateDto;
import com.spavlenko.exception.ConstraintsViolationException;
import com.spavlenko.exception.EntityNotFoundException;
import com.spavlenko.service.ExchangeRateService;
import com.spavlenko.service.UserService;

/**
 * Exchange controller
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 *
 */
@RestController
@RequestMapping("/v1/rates")
public class ExchangeRatesController {

    @Autowired
    private ExchangeRateService exchangeRateService;
    @Autowired
    private UserService userService;

    @Autowired
    private ExchangeRateMapper exchangeRateMapper;

    @GetMapping("/{userId}/currencies/{from}/currencies/{to}")
    public ExchangeRateDto createAndGetRate(@Valid @PathVariable Long userId, @Valid @PathVariable Currency from,
            @Valid @PathVariable Currency to) throws EntityNotFoundException, ConstraintsViolationException {
        User user = userService.find(userId);
        ExchangeRate rate = exchangeRateService.create(user, from, to);
        return exchangeRateMapper.toExchangeRateDto(rate);
    }

    @GetMapping("/{userId}")
    public List<ExchangeRateDto> getRecentRequests(@Valid @PathVariable Long userId) {
        User user = null;
        try {
            user = userService.find(userId);
        } catch (EntityNotFoundException e) {
            return new ArrayList<>();
        }
        return exchangeRateMapper.toExchangeRateDtoList(exchangeRateService.getRecent(user));
    }

    @GetMapping("/{from}/currencies/{to}")
    public List<ExchangeRateDto> getRecentRates(@Valid @PathVariable Currency from, @Valid @PathVariable Currency to) {
        return exchangeRateMapper.toExchangeRateDtoList(exchangeRateService.find(from, to));
    }
}
