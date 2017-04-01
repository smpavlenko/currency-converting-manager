package com.spavlenko.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spavlenko.domain.Currency;

/**
 * Exchange Rate data transfer object
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeRateDto {

    @NotNull(message = "Created date can not be null!")
    private ZonedDateTime dateCreated;

    @NotNull(message = "From currency can not be null!")
    private Currency from;

    @NotNull(message = "To currency can not be null!")
    private Currency to;

    @NotNull(message = "Exchange rate can not be null!")
    private BigDecimal rate;

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

}
