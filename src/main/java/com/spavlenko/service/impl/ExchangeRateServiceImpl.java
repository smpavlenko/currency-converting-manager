package com.spavlenko.service.impl;

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

    @Override
    public ExchangeRate create(ExchangeRate rate) throws ConstraintsViolationException {
        ExchangeRate createdRate = null;
        try {
            createdRate = exchangeRateRepository.save(rate);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintsViolationException(e.getMessage());
        }
        return createdRate;
    }

    @Override
    public List<ExchangeRate> getRecent(User user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ExchangeRate> find(Currency from, Currency to) {
        return exchangeRateRepository.findByCurrencyFromAndCurrencyToOrderByDateCreated(from, to);
    }

}
