package com.example.jwallet.rate.rate.entity;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConvertCurrencyRequest {

	private String sourceCurrency;
	private String targetCurrency;
	private BigDecimal amount;

}
