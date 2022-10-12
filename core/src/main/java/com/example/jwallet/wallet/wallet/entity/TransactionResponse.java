package com.example.jwallet.wallet.wallet.entity;

import java.math.BigDecimal;

import com.example.jwallet.core.entity.Response;

import jakarta.transaction.Transaction;

public class TransactionResponse extends Response<TransactionResponse.TransactionResponseData> {

	public static TransactionResponse of(TransactionResponse.TransactionResponseData data) {
		TransactionResponse response = new TransactionResponse();
		response.setData(data);
		return response;
	}

	public static class TransactionResponseData {
		private long transactionId;
		private BigDecimal balance;
		private TransactionType type;
		private long walletId;
		private BigDecimal amount;
		private String currency;

		public long getTransactionId() {
			return transactionId;
		}

		public void setTransactionId(long transactionId) {
			this.transactionId = transactionId;
		}

		public BigDecimal getBalance() {
			return balance;
		}

		public void setBalance(BigDecimal balance) {
			this.balance = balance;
		}

		public TransactionType getType() {
			return type;
		}

		public void setType(TransactionType type) {
			this.type = type;
		}

		public long getWalletId() {
			return walletId;
		}

		public void setWalletId(long walletId) {
			this.walletId = walletId;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}
	}
}
