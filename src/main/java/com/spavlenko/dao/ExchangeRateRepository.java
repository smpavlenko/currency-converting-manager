package com.spavlenko.dao;

import org.springframework.data.repository.CrudRepository;

import com.spavlenko.domain.ExchangeRate;

/**
 * Exchange Rate repository
 * 
 * @author sergii.pavlenko
 * @since Apr 1, 2017
 */
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {

}
