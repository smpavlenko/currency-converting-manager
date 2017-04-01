package com.spavlenko.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.spavlenko.domain.Currency;
import com.spavlenko.domain.ExchangeRate;

/**
 * Exchange Rate repository
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 */
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {

    List<ExchangeRate> findByCurrencyFromAndCurrencyToOrderByDateCreated(Currency currencyFrom, Currency currencyTo);
}
