package com.spavlenko.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spavlenko.domain.Currency;
import com.spavlenko.service.RateGatewayService;

/**
 * Implementation of RateGatewayService which uses Open Exchange third party
 * service to convert one currency to another. https://openexchangerates.org
 *
 * @author sergii.pavlenko
 * @since Apr 9, 2017
 */
@Service
public class OpenExchangeRateGatewayService implements RateGatewayService {
    private final static String URL = "https://openexchangerates.org/api/latest.json?app_id=f68803a64b2d41b98ba0a246fc24f03c";
    private final static RestTemplate REST_TEMPLATE = new RestTemplate();

    @Override
    public BigDecimal retrieveExchangeRate(Currency from, Currency to) {
        Map<Currency, BigDecimal> map = retrieveExchangeRates();
        BigDecimal valueFrom = map.get(from);
        BigDecimal valueTo = map.get(to);

        return valueTo.divide(valueFrom, 5, RoundingMode.HALF_UP);
    }

    @Override
    public Map<Currency, BigDecimal> retrieveExchangeRates() {
        String body = REST_TEMPLATE.getForObject(URL, String.class);
        JSONObject json = new JSONObject(body);
        JSONObject rates = (JSONObject) json.get("rates");

        Map<Currency, BigDecimal> result = new HashMap<>();
        for (Currency currency : Currency.values()) {
            Double currencyRate = Double.valueOf(rates.get(currency.name()).toString());
            result.put(currency, BigDecimal.valueOf(currencyRate));
        }

        return result;
    }

}
