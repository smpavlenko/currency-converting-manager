package com.spavlenko.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final static RestTemplate REST_TEMPLATE = new RestTemplate();

    @Value("${rateGateway.url}")
    private String gatewayUrl;

    @Override
    @Cacheable(cacheNames = "exchangeRates")
    public BigDecimal retrieveExchangeRate(Currency from, Currency to) {
        Map<Currency, BigDecimal> map = retrieveExchangeRates();
        BigDecimal valueFrom = map.get(from);
        BigDecimal valueTo = map.get(to);
        return valueTo.divide(valueFrom, 5, RoundingMode.HALF_UP);
    }

    @Override
    public Map<Currency, BigDecimal> retrieveExchangeRates() {
        String body = REST_TEMPLATE.getForObject(gatewayUrl, String.class);
        JSONObject json = new JSONObject(body);
        JSONObject rates = (JSONObject) json.get("rates");

        Map<Currency, BigDecimal> result = new HashMap<>();
        for (Currency currency : Currency.values()) {
            Double currencyRate = Double.valueOf(rates.get(currency.name()).toString());
            result.put(currency, BigDecimal.valueOf(currencyRate));
        }

        return result;
    }

    @CacheEvict(value = "exchangeRates", allEntries = true)
    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000, initialDelay = 100)
    public void evictAllCaches() {
        // clears cache once a day
    }

}
