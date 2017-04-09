package com.spavlenko.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        String body = REST_TEMPLATE.getForObject(URL, String.class);
        JSONObject json = new JSONObject(body);
        JSONObject rates = (JSONObject) json.get("rates");
        Double valueFrom = Double.valueOf(rates.get(from.name()).toString());
        Double valueTo = Double.valueOf(rates.get(to.name()).toString());

        return BigDecimal.valueOf(valueTo).divide(BigDecimal.valueOf(valueFrom), 5, RoundingMode.HALF_UP);
    }

}
