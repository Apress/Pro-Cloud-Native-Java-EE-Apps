package com.example.jwallet.wallet.wallet.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

import com.example.jwallet.core.entity.AbstractEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "WALLET_TRANSACTION")
@NamedQuery(name = Transaction.FIND_ALL_WALLET_TRANSACTIONS,
		query = "SELECT t from Transaction t where t.wallet.id = :" + Transaction.WALLET_ID_PARAM)
@Getter
@Setter
public class Transaction extends AbstractEntity {
	public static final String FIND_ALL_WALLET_TRANSACTIONS = "Transaction.findAll";
	public static final String WALLET_ID_PARAM = "walledId";
	private String currency;
	private BigDecimal amount;
	private TransactionType type;

	@ManyToOne
	private Wallet wallet;

	public TransactionResponse.TransactionResponseData toTransactionResponseData() {
		TransactionResponse.TransactionResponseData data = new TransactionResponse.TransactionResponseData();
		data.setTransactionId(this.getId());
		data.setWalletId(this.getWallet().getId());
		data.setBalance(this.getWallet().getBalance());
		data.setType(this.getType());
		data.setAmount(this.getAmount());
		data.setCurrency(this.getCurrency());

		return data;
	}

}
