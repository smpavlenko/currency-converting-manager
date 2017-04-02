package com.spavlenko.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.spavlenko.dao.ExchangeRateRepository;
import com.spavlenko.domain.Currency;
import com.spavlenko.domain.ExchangeRate;
import com.spavlenko.domain.User;
import com.spavlenko.exception.ConstraintsViolationException;
import com.spavlenko.service.ExchangeRateService;
import com.spavlenko.service.RateGatewayService;

/**
 * Default exchange rate service implementation
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 */
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private RateGatewayService rateGatewayService;

    @Override
    public ExchangeRate create(User user, Currency from, Currency to) throws ConstraintsViolationException {

        BigDecimal rate = rateGatewayService.retrieveExchangeRate(from, to);
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setUser(user);
        exchangeRate.setCurrencyFrom(from);
        exchangeRate.setCurrencyTo(to);
        exchangeRate.setRate(rate);
        ExchangeRate createdRate = null;
        try {
            createdRate = exchangeRateRepository.save(exchangeRate);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintsViolationException(e.getMessage());
        }
        return createdRate;
    }

    @Override
    public List<ExchangeRate> getRecent(User user) {
        return exchangeRateRepository.findTop5ByUserOrderByDateCreatedDesc(user);
    }

    @Override
    public List<ExchangeRate> find(Currency from, Currency to) {
        return exchangeRateRepository.findByCurrencyFromAndCurrencyToOrderByDateCreated(from, to);
    }

}
