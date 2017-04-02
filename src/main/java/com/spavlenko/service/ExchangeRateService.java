package com.spavlenko.service;

import java.util.List;

import com.spavlenko.domain.Currency;
import com.spavlenko.domain.ExchangeRate;
import com.spavlenko.domain.User;
import com.spavlenko.exception.ConstraintsViolationException;

/**
 * Exchange Rate service
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 */
public interface ExchangeRateService {

    /**
     * Creates and returns new exchange rate
     * 
     * @param user
     *            user which creates request
     * @param from
     *            from currency
     * @param to
     *            to currency
     * @return created exchange rate
     * @throws ConstraintsViolationException
     *             when some constraints are violated
     */
    ExchangeRate create(User user, Currency from, Currency to) throws ConstraintsViolationException;

    /**
     * Gets recent exchange rates for user
     * 
     * @param user
     *            target user
     * @return a list of five most recent exchange rates
     */
    List<ExchangeRate> getRecent(User user);

    /**
     * Gets all exchange rates between two currencies
     * 
     * @param from
     *            from currency
     * @param to
     *            to currency
     * @return a list of exchange rates
     */
    List<ExchangeRate> find(Currency from, Currency to);
}
