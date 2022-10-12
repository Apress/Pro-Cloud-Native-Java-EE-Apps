package com.example.jwallet.wallet.wallet.entity;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import com.example.jwallet.core.entity.AbstractEntity;
import com.example.jwallet.wallet.wallet.control.WalletOverdrawnException;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

@Entity
@Getter
@Setter
@NamedQuery(name = "getMaxWalletBalance",
		query = "SELECT w FROM Wallet  w WHERE w.balance = (SELECT MAX(wl.balance) FROM Wallet wl WHERE wl.currency = :currency)")
@NamedQuery(name = "getEmptyWallets",
		query = "SELECT w FROM Wallet  w WHERE w.transactions IS EMPTY")
@NamedQuery(name = "getZeroWallets",
		query = "SELECT w FROM Wallet  w WHERE w.balance = 0 AND  w.transactions IS NOT EMPTY")

public class Wallet extends AbstractEntity {

	private String authUserId;

	private String currency;
	private BigDecimal balance;

	@OneToMany(mappedBy = "wallet")
	private Set<Transaction> transactions;

	public synchronized void credit(final BigDecimal amount) {
		setBalance(getBalance().add(amount));
	}

	public synchronized void debit(final BigDecimal amount) {
		BigDecimal newBalance = getBalance().subtract(amount);
		if (newBalance.signum() == -1) {
			throw new WalletOverdrawnException();
		} else {
			setBalance(newBalance);
		}
	}

}
