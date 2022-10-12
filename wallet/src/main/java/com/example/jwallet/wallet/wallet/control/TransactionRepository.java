package com.example.jwallet.wallet.wallet.control;

import java.util.UUID;

import com.example.jwallet.core.control.BaseRepository;
import com.example.jwallet.wallet.wallet.entity.Transaction;
import jakarta.enterprise.context.RequestScoped;

public class TransactionRepository extends BaseRepository<Transaction, UUID> {

	public TransactionRepository() {
		super(Transaction.class);
	}
}
