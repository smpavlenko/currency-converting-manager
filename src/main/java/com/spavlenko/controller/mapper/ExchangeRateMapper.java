package com.spavlenko.controller.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.spavlenko.domain.ExchangeRate;
import com.spavlenko.dto.ExchangeRateDto;

/**
 * Exchange Rate Mapper
 * 
 * @author sergii.pavlenko
 * @since Apr 2, 2017
 */
@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {

    /**
     * ExchangeRate -> ExchangeRateDto mapping
     */
    @Mapping(target = "from", source = "currencyFrom")
    @Mapping(target = "to", source = "currencyTo")
    ExchangeRateDto toExchangeRateDto(ExchangeRate source);

    /**
     * List<ExchangeRate> -> List<ExchangeRateDto> mapping
     */
    List<ExchangeRateDto> toExchangeRateDtoList(List<ExchangeRate> sourceList);
}
