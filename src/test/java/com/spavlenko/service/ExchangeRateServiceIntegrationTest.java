package com.spavlenko.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spavlenko.CurrencyConverterApplication;
import com.spavlenko.domain.Currency;
import com.spavlenko.domain.ExchangeRate;
import com.spavlenko.domain.User;
import com.spavlenko.exception.ConstraintsViolationException;

/**
 * Integration tests for ExchangeRateService
 * 
 * @author sergii.pavlenko
 * @since Apr 2, 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CurrencyConverterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExchangeRateServiceIntegrationTest {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private UserService userService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void create() throws Exception {
        // given
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCurrencyFrom(Currency.EUR);
        exchangeRate.setCurrencyTo(Currency.USD);
        exchangeRate.setRate(BigDecimal.ONE);
        exchangeRate.setUser(userService.find(1L));

        // when
        ExchangeRate resultExchangeRate = exchangeRateService.create(exchangeRate);

        // then
        assertThat(resultExchangeRate.getCurrencyFrom(), equalTo(Currency.EUR));
        assertThat(resultExchangeRate.getCurrencyTo(), equalTo(Currency.USD));
        assertThat(resultExchangeRate.getRate(), equalTo(BigDecimal.ONE));
        assertThat(resultExchangeRate.getUser(), notNullValue());
        assertThat(resultExchangeRate.getId(), notNullValue());
    }

    @Test
    public void create_exception() throws Exception {
        // given
        expectedException.expect(ConstraintsViolationException.class);
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCurrencyFrom(Currency.EUR);
        exchangeRate.setCurrencyTo(Currency.USD);
        exchangeRate.setRate(BigDecimal.ONE);

        // when
        exchangeRateService.create(exchangeRate);
    }

    @Test
    public void find() throws Exception {
        // when
        List<ExchangeRate> resultRates = exchangeRateService.find(Currency.AUD, Currency.GBP);

        assertThat(resultRates, hasSize(2));
        assertThat(resultRates.stream().map(ExchangeRate::getCurrencyFrom).collect(Collectors.toSet()), hasSize(1));
        assertThat(resultRates.stream().map(ExchangeRate::getCurrencyTo).collect(Collectors.toSet()), hasSize(1));
        assertThat(resultRates.stream().map(ExchangeRate::getRate).collect(Collectors.toSet()), hasSize(2));
        assertThat(resultRates.stream().map(ExchangeRate::getUser).map(User::getUsername).collect(Collectors.toSet()),
                hasSize(1));
        List<ZonedDateTime> dates = resultRates.stream().map(ExchangeRate::getDateCreated).collect(Collectors.toList());
        assertThat(dates, hasSize(2));
        assertThat(dates.get(0).isBefore(dates.get(1)), equalTo(true));

    }
}
