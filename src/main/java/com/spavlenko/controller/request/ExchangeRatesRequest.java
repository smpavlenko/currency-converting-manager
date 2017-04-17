package com.spavlenko.controller.request;

import com.spavlenko.domain.Currency;

/**
 * Exchange Rates request
 * 
 * @author sergii.pavlenko
 * @since Apr 17, 2017
 */
public class ExchangeRatesRequest {

    private Currency from;
    private Currency to;

    public ExchangeRatesRequest() {
    }

    public ExchangeRatesRequest(Currency currencyFrom, Currency currencyTo) {
        from = currencyFrom;
        to = currencyTo;
    }

    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        this.to = to;
    }

}
