package com.example.jwallet.rate.rate.providers.apilayer.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.ws.rs.QueryParam;

@Getter
@Setter
public class ApiLayerConvertRequest {

	@QueryParam("from")
	private String fromCurrency;
	@QueryParam("to")
	private String toCurrency;
	@QueryParam("amount")
	private String amount;

}
