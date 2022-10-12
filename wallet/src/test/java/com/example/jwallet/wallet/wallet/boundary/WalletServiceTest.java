package com.example.jwallet.wallet.wallet.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.jwallet.wallet.wallet.control.TransactionRepository;
import com.example.jwallet.wallet.wallet.control.WalletRepository;
import com.example.jwallet.wallet.wallet.control.WalletService;
import com.example.jwallet.wallet.wallet.entity.BalanceResponse;
import com.example.jwallet.wallet.wallet.entity.Wallet;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {
	@Mock
	WalletRepository walletRepository;
	@Mock
	TransactionRepository txnRepo;
	WalletService walletService;
	static String walletCurrency;
	static BigDecimal balance;

	@BeforeAll
	public static void initAll() {
		walletCurrency = "GHS";
		balance = new BigDecimal("50");
	}
	@BeforeEach
	void init() {

		walletService = new WalletService(walletRepository, txnRepo, null);
		when(walletRepository.findById(anyLong())).thenAnswer(i -> {
			Wallet wallet = new Wallet();
			wallet.setId(i.getArgument(0));
			wallet.setCurrency(walletCurrency);
			wallet.setBalance(balance);
			return wallet;

		});

	}

	@Test
	void shouldFindWallet() {
		BalanceResponse response = walletService.getBalance(1L);
		assertNotNull(response);
		assertEquals(1L, response.getData().getWalletId());
		assertEquals(balance, response.getData().getBalance());
		assertEquals(walletCurrency, response.getData().getCurrency());
	}
}
