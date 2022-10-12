package com.example.jwallet.wallet.wallet.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.example.jwallet.core.entity.Response;

public class TransactionsResponse extends Response<TransactionsResponse.TransactionsResponseData> {

	public static TransactionsResponse of(Collection<TransactionResponse.TransactionResponseData> data) {
		TransactionsResponseData transactionResponseData = new TransactionsResponseData();
		transactionResponseData.addAll(data);

		TransactionsResponse response = new TransactionsResponse();
		response.setData(transactionResponseData);
		return response;
	}

	public static class TransactionsResponseData extends HashSet<TransactionResponse.TransactionResponseData> {
	}
}
