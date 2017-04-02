package com.spavlenko.service;

import java.math.BigDecimal;

import com.spavlenko.domain.Currency;

/**
 * Rate Gateway service which gives access to third party Exchange Rate services
 * 
 * @author sergii.pavlenko
 * @since Apr 2, 2017
 */
public interface RateGatewayService {

    /**
     * Retrieves exchange rate between currencies from third party application
     * 
     * @param from
     *            currency from
     * @param to
     *            currency from
     * @return exchange rate with scale 5
     */
    BigDecimal retrieveExchangeRate(Currency from, Currency to);
}
