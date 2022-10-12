package com.example.jwallet.wallet.wallet.entity;

import javax.money.CurrencyUnit;

public class CreateWalletRequest {

	private String currency;
	private CurrencyUnit currencyUnit;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
