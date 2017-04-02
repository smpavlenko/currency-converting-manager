package com.spavlenko.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    private RateGatewayService rateGatewayService;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private UserService userService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        Mockito.when(rateGatewayService.retrieveExchangeRate(Mockito.any(Currency.class), Mockito.any(Currency.class)))
                .thenReturn(BigDecimal.valueOf(1.1d));
    }

    @Test
    public void create() throws Exception {

        // when
        ExchangeRate resultExchangeRate = exchangeRateService.create(userService.find(1L), Currency.EUR, Currency.USD);

        // then
        assertThat(resultExchangeRate.getCurrencyFrom(), equalTo(Currency.EUR));
        assertThat(resultExchangeRate.getCurrencyTo(), equalTo(Currency.USD));
        assertThat(resultExchangeRate.getRate(), equalTo(BigDecimal.valueOf(1.1d)));
        assertThat(resultExchangeRate.getUser(), notNullValue());
        assertThat(resultExchangeRate.getId(), notNullValue());
    }

    @Test
    public void create_exception() throws Exception {
        // given
        expectedException.expect(ConstraintsViolationException.class);

        // when
        exchangeRateService.create(null, Currency.EUR, Currency.USD);
    }

    @Test
    public void find() throws Exception {
        // when
        List<ExchangeRate> resultRates = exchangeRateService.find(Currency.AUD, Currency.GBP);

        // then
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

    @Test
    public void find_same() throws Exception {
        // when
        List<ExchangeRate> resultRates = exchangeRateService.find(Currency.AUD, Currency.AUD);

        assertThat(resultRates, hasSize(0));
    }

    @Test
    public void getRecent() throws Exception {
        // when
        List<ExchangeRate> resultRates = exchangeRateService.getRecent(userService.find(2L));

        // then
        assertThat(resultRates, hasSize(2));

    }

}
