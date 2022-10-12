package com.example.jwallet.rate.rate.providers.apilayer.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiLayerConvertResponse {

	private boolean success;
	private Info info;
	private LocalDate date;
	private BigDecimal result;

	@Getter
	@Setter
	public static class Info {
		private Long timestamp;
		private BigDecimal rate;

	}
}
