package com.example.jwallet.wallet.wallet.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

import com.example.jwallet.core.entity.Response;

@Getter
@Setter
public class BalanceResponse extends Response<BalanceResponse.BalanceResponseData> {

	public static BalanceResponse of(BalanceResponseData data) {
		BalanceResponse response = new BalanceResponse();
		response.setData(data);
		return response;
	}

	@Getter
	@Setter
	public static class BalanceResponseData {
		private Long walletId;
		private BigDecimal balance;
		private String currency;

	}
}

