package com.spavlenko.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.spavlenko.domain.Currency;
import com.spavlenko.domain.ExchangeRate;
import com.spavlenko.domain.User;

/**
 * Exchange Rate repository
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 */
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {

    /**
     * Gets a list of exchanged rates ordered by created date
     * 
     * @param currencyFrom
     *            currency from
     * @param currencyTo
     *            currency to
     * @return a list of exchange rates
     */
    List<ExchangeRate> findByCurrencyFromAndCurrencyToOrderByDateCreated(Currency currencyFrom, Currency currencyTo);

    List<ExchangeRate> findTop5ByUserOrderByDateCreatedDesc(User user);
}
