package com.spavlenko.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spavlenko.dao.ExchangeRateRepository;
import com.spavlenko.dao.UserRepository;
import com.spavlenko.domain.Currency;
import com.spavlenko.domain.ExchangeRate;
import com.spavlenko.domain.User;
import com.spavlenko.service.RateGatewayService;

/**
 * Service which is periodically getting currency rates and stores them
 * 
 * @author sergii.pavlenko
 * @since May 9, 2017
 *
 */
@Service
public class ScheduledGettingRateService {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private RateGatewayService rateGatewayService;

    public ScheduledGettingRateService() {
        scheduler.scheduleAtFixedRate(new PollingTask(), 1, 24, TimeUnit.HOURS);
    }

    private class PollingTask implements Runnable {
        @Override
        public void run() {
            User user = userRepository.findByUsername("test2");
            Map<Currency, BigDecimal> map = rateGatewayService.retrieveExchangeRates();

            for (Currency from : Currency.values()) {
                for (Currency to : Currency.values()) {
                    ExchangeRate exchangeRate = new ExchangeRate();
                    exchangeRate.setUser(user);
                    exchangeRate.setCurrencyFrom(from);
                    exchangeRate.setCurrencyTo(to);
                    exchangeRate.setRate(map.get(to).divide(map.get(from), 5, RoundingMode.HALF_UP));
                    exchangeRateRepository.save(exchangeRate);
                }
            }
        }

    }
}
