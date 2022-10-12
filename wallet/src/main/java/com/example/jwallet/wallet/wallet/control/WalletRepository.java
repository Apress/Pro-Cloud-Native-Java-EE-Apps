package com.example.jwallet.wallet.wallet.control;

import java.util.UUID;

import com.example.jwallet.core.control.BaseRepository;
import com.example.jwallet.wallet.wallet.entity.Wallet;
import jakarta.enterprise.context.RequestScoped;

public class WalletRepository extends BaseRepository<Wallet, Long> {

	public WalletRepository() {
		super(Wallet.class);
	}

	@Override
	public Wallet findById(final Long id) {
		final Wallet wallet = super.findById(id);
		if (wallet == null) {
			throw new WalletNotFoundException();
		}
		return wallet;
	}
}
