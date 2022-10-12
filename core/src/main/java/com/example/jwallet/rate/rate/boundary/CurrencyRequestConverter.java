package com.example.jwallet.rate.rate.boundary;

import java.math.BigDecimal;

import org.eclipse.microprofile.config.spi.Converter;

import com.example.jwallet.rate.rate.entity.ConvertCurrencyRequest;

public class CurrencyRequestConverter implements Converter<ConvertCurrencyRequest> {
	@Override
	public ConvertCurrencyRequest convert(final String value) {
		if (value == null || value.isBlank()) {
			throw new NullPointerException("Value must not be null or empty string");
		}

		final String[] split = value.split(",");
		if (split.length != 3) {
			throw new IllegalArgumentException("The resulting array has less than 3 elements.");
		}
		return ConvertCurrencyRequest.builder().sourceCurrency(split[0]).targetCurrency(split[1])
				.amount(new BigDecimal(split[2])).build();
	}
}
